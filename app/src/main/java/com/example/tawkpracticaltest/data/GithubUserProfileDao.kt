package com.example.tawkpracticaltest.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tawkpracticaltest.data.models.GithubUserProfile


@Dao
interface GithubUserProfileDao {

    @Query("SELECT * FROM github_user_profile WHERE login = :login")
    suspend fun getUserProfile(login: String) : GithubUserProfile

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProfile(githubUserProfile: GithubUserProfile)

}