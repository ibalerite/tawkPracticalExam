package com.example.tawkpracticaltest.core

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.tawkpracticaltest.data.*
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DependencyProvider {
    private lateinit var applicationContext: Context

    private val DATABASE_NAME = "tawk_database"
    private val BASE_URL = "https://api.github.com"
    private val gson = Gson()
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .build()

    private val githubService : GithubService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(GithubService::class.java)

    private val db by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    fun init(context: Application) {
        this.applicationContext = context
    }

    fun provideGithubRepository(): Lazy<GithubRepository> =
        lazy {
            GithubRepository.getInstance(
                localDataSource = GithubLocalDataSource(
                    db.githubUserDao(),
                    db.githubUserProfileDao(),
                    provideDispatcherProvider().value
                ),
                remoteDataSource = GithubRemoteDataSource(githubService)
            )
        }

    fun provideDispatcherProvider(): Lazy<CoroutinesDispatcherProvider> = lazy {
        CoroutinesDispatcherProvider()
    }

}

