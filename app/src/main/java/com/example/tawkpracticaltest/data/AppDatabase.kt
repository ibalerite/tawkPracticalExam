package com.example.tawkpracticaltest.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tawkpracticaltest.data.models.GithubUser
import com.example.tawkpracticaltest.data.models.GithubUserProfile

@Database(entities = [GithubUser::class, GithubUserProfile::class], version = 7)
abstract class AppDatabase : RoomDatabase() {
    abstract fun githubUserDao(): GithubUserDao
    abstract fun githubUserProfileDao(): GithubUserProfileDao
}