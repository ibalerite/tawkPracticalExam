package com.example.tawkpracticaltest.data

import com.example.tawkpracticaltest.data.models.GithubUser
import com.example.tawkpracticaltest.data.models.GithubUserProfile
import com.example.tawkpracticaltest.util.Result

class GithubRepository constructor(
    private val localSource: GithubLocalDataSource,
    private val remoteSource: GithubRemoteDataSource
) {

    suspend fun getUsersFromCache(): Result<List<GithubUser>> {
        val localUsers = localSource.getUsers()
        if (localUsers is Result.Success) return localUsers
        return Result.Error(Exception("Unable to fetch data"))
    }

    suspend fun updateUser(user: GithubUser): Result<Int> {
        return localSource.updateUser(user)
    }

    suspend fun getUserProfile(login: String): Result<GithubUserProfile> {
        return fetchUserProfileFromRemoteOrLocal(login)
    }

    suspend fun getUsers(searchString: String): Result<List<GithubUser>> {
        val localUsers = localSource.getUsers(searchString)
        if (localUsers is Result.Success) return localUsers
        return Result.Error(Exception("No users found"))
    }

    suspend fun getUsers(since: Long = 0L): Result<List<GithubUser>> {
        return fetchUsersFromRemoteOrLocal(since)
    }

    private suspend fun fetchUsersFromRemoteOrLocal(since: Long = 0L): Result<List<GithubUser>> {
        val remoteUsers = remoteSource.getUsers(since)
        if (remoteUsers is Result.Success) {
            refreshUserLocalDataSource(remoteUsers.data)
            return remoteUsers
        }

        val localUsers = localSource.getUsers()
        if (localUsers is Result.Success) return localUsers
        return Result.Error(Exception("Unable to fetch data"))
    }

    private suspend fun refreshUserLocalDataSource(users: List<GithubUser>) {
        if (users.isNotEmpty())
            for (user in users) {
                localSource.saveUser(user)
            }
    }

    private suspend fun fetchUserProfileFromRemoteOrLocal(login: String): Result<GithubUserProfile> {
        val remoteUserProfile = remoteSource.getUserProfile(login)
        if (remoteUserProfile is Result.Success) {
            refreshUserProfileLocalDataSource(remoteUserProfile.data)
            return remoteUserProfile
        }

        val localUserProfile = localSource.getUserProfile(login)
        if (localUserProfile is Result.Success) return localUserProfile
        return Result.Error(Exception("Unable to fetch data"))
    }

    private suspend fun refreshUserProfileLocalDataSource(userProfile: GithubUserProfile) {
        localSource.saveUserProfile(userProfile)
    }

    companion object {
        @Volatile
        private var INSTANCE: GithubRepository? = null

        fun getInstance(
            localDataSource: GithubLocalDataSource,
            remoteDataSource: GithubRemoteDataSource
        ): GithubRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: GithubRepository(
                    localDataSource,
                    remoteDataSource
                ).also { INSTANCE = it }
            }
        }
    }
}