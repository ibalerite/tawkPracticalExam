package com.example.tawkpracticaltest.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tawkpracticaltest.data.AppDatabase
import com.example.tawkpracticaltest.data.GithubUserProfileDao
import com.example.tawkpracticaltest.data.models.GithubUserProfile
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GithubUserProfileDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var appDatabase: AppDatabase
    private lateinit var githubUserProfileDao: GithubUserProfileDao

    @Before
    fun init() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        githubUserProfileDao = appDatabase.githubUserProfileDao()
    }

    @After
    fun teardown() {
        appDatabase.close()
    }

    @Test
    fun updateUserProfileToDatabase() = runBlocking {
        var newUserProfile = GithubUserProfile(
            login = "ichu",
            id = 1,
            nodeId = "",
            avatarUrl = "",
            gravatarId = "",
            url = "",
             htmlUrl = "",
             followersUrl = "",
             gistsUrl = "",
             starredUrl = "",
             subscriptionsUrl = "",
             organizationsUrl = "",
             reposUrl = "",
             receivedEventsUrl = "",
             type = "",
             siteAdmin = false,
             name = "",
             company = "",
             blog = "",
             location = "",
             email = "",
             hireable = true,
             bio = "",
             twitterUsername = "",
             publicRepos = 0,
             publicGists = 0,
             followers = 0L,
             following = 0L,
             createdAt = "",
             updatedAt = ""
        )

        githubUserProfileDao.saveUserProfile(newUserProfile)

        val addedGithubUserProfile = githubUserProfileDao.getUserProfile("ichu")

        Truth.assertThat(addedGithubUserProfile.login)
            .isEqualTo(newUserProfile.login)
    }


}