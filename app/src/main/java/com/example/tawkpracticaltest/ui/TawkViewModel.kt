package com.example.tawkpracticaltest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tawkpracticaltest.data.CoroutinesDispatcherProvider
import com.example.tawkpracticaltest.data.GithubRepository
import com.example.tawkpracticaltest.data.models.GithubUser
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.tawkpracticaltest.util.Result

class TawkViewModel constructor(
    private val githubRepository: GithubRepository,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {
    private val _uiState = MutableLiveData<TawkUiState>()
    val uiState: LiveData<TawkUiState>
        get() = _uiState

    private var githubJob: Job? = null

    fun getUsers(since: Long = 0L) {
        if (githubJob?.isActive == true) {
            return
        }
        githubJob = launchGetUsers(since)
    }

    fun getUserProfile(login: String) {
        if (githubJob?.isActive == true) {
            return
        }
        githubJob = launchGetUserProfile(login)
    }

    fun getUsers(searchString: String) = viewModelScope.launch(dispatcherProvider.computation) {
        val result = githubRepository.getUsers(searchString)
        withContext(dispatcherProvider.main) {
            when (result) {
                is Result.Success ->
                    emitUiState(TawkUiState.SearchResults(result.data))
                is Result.Error ->
                    emitUiState(TawkUiState.Error(result.exception.message.toString()))
            }
        }
    }

    fun updateUser(user: GithubUser) = viewModelScope.launch(dispatcherProvider.computation) {
        githubRepository.updateUser(user)
    }

    private fun launchGetUsers(since: Long): Job {
        return viewModelScope.launch(dispatcherProvider.computation) {
            val result = githubRepository.getUsers(since)

            withContext(dispatcherProvider.main) {
                when (result) {
                    is Result.Success ->
                        emitUiState(TawkUiState.UsersRetrieved(result.data))
                    is Result.Error ->
                        emitUiState(TawkUiState.Error(result.exception.message.toString()))
                }
            }
        }
    }

    private fun launchGetUserProfile(login: String): Job {
        return viewModelScope.launch(dispatcherProvider.computation) {
            val result = githubRepository.getUserProfile(login)

            withContext(dispatcherProvider.main) {
                when (result) {
                    is Result.Success ->
                        emitUiState(TawkUiState.UserProfileReceived(result.data))
                    is Result.Error ->
                        emitUiState(TawkUiState.Error(result.exception.message.toString()))
                }
            }
        }
    }

    private fun emitUiState(state: TawkUiState) {
        _uiState.value = state
    }
}