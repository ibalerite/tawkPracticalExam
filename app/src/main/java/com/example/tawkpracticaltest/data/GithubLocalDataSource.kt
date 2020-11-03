package com.example.tawkpracticaltest.data

import com.example.tawkpracticaltest.data.models.GithubUser
import com.example.tawkpracticaltest.data.models.GithubUserProfile
import com.example.tawkpracticaltest.util.Result
import kotlinx.coroutines.withContext
import kotlin.Exception

class GithubLocalDataSource constructor(
    private val userDao: GithubUserDao,
    private val userProfileDao: GithubUserProfileDao,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) {
    suspend fun getUsers() : Result<List<GithubUser>> = withContext(dispatcherProvider.io) {
        return@withContext try {
            Result.Success(userDao.getAll())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun  getUsers(search: String): Result<List<GithubUser>> = withContext(dispatcherProvider.io) {
        return@withContext try {
            Result.Success(userDao.getAll(search))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getUser(login: String) : Result<GithubUser> = withContext(dispatcherProvider.io) {
        return@withContext try {
            Result.Success(userDao.getUser(login))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getUserProfile(login: String) : Result<GithubUserProfile> = withContext(dispatcherProvider.io) {
        return@withContext try {
            Result.Success(userProfileDao.getUserProfile(login))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun saveUser(user: GithubUser) = withContext(dispatcherProvider.io) {
        userDao.insertUser(user)
    }

    suspend fun saveUserProfile(userProfile: GithubUserProfile) = withContext(dispatcherProvider.io) {
        userProfileDao.saveUserProfile(userProfile)
    }

    suspend fun updateUser(user: GithubUser): Result<Int> = withContext(dispatcherProvider.io) {
        return@withContext try {
            Result.Success(userDao.updateUser(user))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun deleteAllUsers() = withContext(dispatcherProvider.io) {
        userDao.deleteAll()
    }
}