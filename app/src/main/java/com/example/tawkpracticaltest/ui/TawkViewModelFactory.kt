package com.example.tawkpracticaltest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tawkpracticaltest.core.DependencyProvider

class TawkViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass != TawkViewModel::class.java) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
        return TawkViewModel(
             DependencyProvider.provideGithubRepository().value,
            DependencyProvider.provideDispatcherProvider().value
        ) as T
    }

}