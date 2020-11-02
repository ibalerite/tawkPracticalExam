package com.example.tawkpracticaltest.ui

import android.app.Application
import com.example.tawkpracticaltest.core.DependencyProvider

class TawkApp : Application() {
    override fun onCreate() {
        super.onCreate()

        DependencyProvider.init(this)
    }
}