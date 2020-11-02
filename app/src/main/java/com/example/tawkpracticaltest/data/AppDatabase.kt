package com.example.tawkpracticaltest.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tawkpracticaltest.data.models.GithubUser

@Database(entities = arrayOf(GithubUser::class), version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun githubUserDao(): GithubUserDao
}