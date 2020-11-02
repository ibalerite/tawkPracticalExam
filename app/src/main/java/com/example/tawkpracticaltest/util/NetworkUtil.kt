package com.example.tawkpracticaltest.util

import okio.IOException


suspend fun <T : Any> safeApiCall(
    call: suspend () -> Result<T>,
    errorMessage: String = "Error retrieving data"
): Result<T> {
    return try {
        call()
    } catch (e: Exception) {
        Result.Error(IOException(errorMessage, e))
    }
}