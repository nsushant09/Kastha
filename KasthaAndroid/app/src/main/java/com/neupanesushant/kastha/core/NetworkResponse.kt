package com.neupanesushant.kastha.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class Response<out T> {
    data class Success<T>(val responseData: T) : Response<T>()
    data class Failure(val errorMessage: String) : Response<Nothing>()
}

class ResponseResolver<T>(
    private val response: Response<T>,
    private val onFailure: suspend (String) -> Unit = {},
    private val onSuccess: suspend (T) -> Unit
) {
    suspend operator fun invoke(){
        if (response is Response.Success) {
            onSuccess(response.responseData)
        }
        if (response is Response.Failure) {
            onFailure(response.errorMessage)
        }
    }
}

