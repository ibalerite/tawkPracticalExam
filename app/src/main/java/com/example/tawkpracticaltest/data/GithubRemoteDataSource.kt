package com.example.tawkpracticaltest.data

import com.example.tawkpracticaltest.util.Result
import com.example.tawkpracticaltest.util.safeApiCall

class GithubRemoteDataSource constructor(
    private val githubService: GithubService
) {
    suspend fun getUsers(since: Long) = safeApiCall(
        call = {
            try{
                val response = githubService.getUsers(since)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        return@safeApiCall Result.Success(body)
                    }
                }
                Result.Error(Exception("Unable to fetch data"))
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    )

    suspend fun getUserProfile(login: String) = safeApiCall(
        call = {
            try{
                val response = githubService.getUser(login)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        return@safeApiCall Result.Success(body)
                    }
                }
                Result.Error(Exception("Unable to fetch data"))
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    )
}