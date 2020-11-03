package com.example.tawkpracticaltest.ui

import com.example.tawkpracticaltest.data.models.GithubUser
import com.example.tawkpracticaltest.data.models.GithubUserProfile


sealed class TawkUiState {

    object Loading: TawkUiState()

    data class UserUpdated(val result: Int): TawkUiState()
    data class UserRetrieved(val user: GithubUser): TawkUiState()
    data class UsersRetrieved(val users: List<GithubUser>): TawkUiState()
    data class UserProfileReceived(val profile: GithubUserProfile): TawkUiState()
    data class SearchResults(val users: List<GithubUser>): TawkUiState()
    data class Error(val message: String): TawkUiState()


}