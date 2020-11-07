package com.example.tawkpracticaltest.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tawkpracticaltest.data.AppDatabase
import com.example.tawkpracticaltest.data.GithubUserDao
import com.example.tawkpracticaltest.data.models.GithubUser
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GithubUserDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var appDatabase: AppDatabase
    private lateinit var githubUserDao: GithubUserDao

    @Before
    fun init() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        githubUserDao = appDatabase.githubUserDao()
    }

    @After
    fun teardown() {
        appDatabase.close()
    }

    @Test
    fun addUserToDatabase() = runBlockingTest {
        val newGithubUser = GithubUser(
            login = "ichu",
            id = 1,
            nodeId = "",
            avatarUrl = "",
            gravatarId = "",
            url = "",
            htmlUrl = null,
            followersUrl = null,
            followingUrl = null,
            gistsUrl = null,
            starredUrl = null,
            subscriptionsUrl = null,
            organizationsUrl = null,
            reposUrl = null,
            eventsUrl = null,
            receivedEventsUrl = null,
            type = null,
            siteAdmin = false,
            notes = null
        )

        githubUserDao.insertUser(newGithubUser)

        val addedGithubUser = githubUserDao.getUser("ichu")

        assertThat(addedGithubUser.login)
            .isEqualTo(newGithubUser.login)
    }

    @Test
    fun updateUserToDatabase() = runBlockingTest {
        val newGithubUser = GithubUser(
            login = "ichu",
            id = 1,
            nodeId = "",
            avatarUrl = "",
            gravatarId = "",
            url = "",
            htmlUrl = null,
            followersUrl = null,
            followingUrl = null,
            gistsUrl = null,
            starredUrl = null,
            subscriptionsUrl = null,
            organizationsUrl = null,
            reposUrl = null,
            eventsUrl = null,
            receivedEventsUrl = null,
            type = null,
            siteAdmin = false,
            notes = null
        )

        githubUserDao.insertUser(newGithubUser)

        newGithubUser.notes = "test"
        githubUserDao.updateUser(newGithubUser)

        val updatedGithubUser = githubUserDao.getUser("ichu")

        assertThat(updatedGithubUser.notes)
            .isEqualTo(newGithubUser.notes)

    }



}