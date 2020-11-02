package com.example.tawkpracticaltest.data

import com.example.tawkpracticaltest.data.models.GithubUser
import com.example.tawkpracticaltest.data.models.GithubUserProfile
import com.example.tawkpracticaltest.util.Result

class GithubRepository constructor(
    private val localSource: GithubLocalDataSource,
    private val remoteSource: GithubRemoteDataSource
) {

    suspend fun updateUser(user: GithubUser) {
        localSource.saveUser(user)
    }

    suspend fun getUserProfile(login: String): Result<GithubUserProfile> {
        return remoteSource.getUserProfile(login)
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
        if(remoteUsers is Result.Success) {
            refreshLocalDataSource(remoteUsers.data)
            return remoteUsers
        }

        val localUsers = localSource.getUsers()
        if(localUsers is Result.Success) return  localUsers
        return Result.Error(Exception("Unable to fetch data"))
    }

    private suspend fun refreshLocalDataSource(users: List<GithubUser>) {
        for (user in users) {
            localSource.saveUser(user)
        }
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