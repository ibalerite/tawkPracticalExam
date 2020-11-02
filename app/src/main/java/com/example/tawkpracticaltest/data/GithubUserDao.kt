package com.example.tawkpracticaltest.data

import androidx.room.*
import com.example.tawkpracticaltest.data.models.GithubUser

@Dao
interface GithubUserDao {

    @Query("SELECT * FROM github_user")
    suspend fun getAll(): List<GithubUser>

    @Query("SELECT * FROM github_user WHERE login LIKE '%' || :search || '%' OR notes LIKE '%' || :search || '%'")
    suspend fun getAll(search: String): List<GithubUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: GithubUser)

    @Delete
    suspend fun delete(user: GithubUser)

    @Query("DELETE FROM github_user")
    suspend fun deleteAll()
}