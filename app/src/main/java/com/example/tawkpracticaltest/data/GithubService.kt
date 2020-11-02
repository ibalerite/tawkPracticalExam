package com.example.tawkpracticaltest.data

import com.example.tawkpracticaltest.data.models.GithubUserProfile
import com.example.tawkpracticaltest.data.models.GithubUser
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET(USERS_ENDPOINT_URL)
    suspend fun getUsers(
        @Query("since") since: Long
    ): Response<List<GithubUser>>

    @GET("$USERS_ENDPOINT_URL/{login}")
    suspend fun getUser(
        @Path("login") login: String
    ): Response<GithubUserProfile>

    companion object {
        private const val USERS_ENDPOINT_URL = "users"
    }

}