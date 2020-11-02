package com.example.tawkpracticaltest.data

import com.example.tawkpracticaltest.data.models.GithubUser
import com.example.tawkpracticaltest.util.Result
import kotlinx.coroutines.withContext
import kotlin.Exception

class GithubLocalDataSource constructor(
    private val userDao: GithubUserDao,
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

    suspend fun saveUser(user: GithubUser)  = withContext(dispatcherProvider.io) {
        userDao.insertUser(user);
    }

    suspend fun deleteAllUsers() = withContext(dispatcherProvider.io) {
        userDao.deleteAll()
    }
}