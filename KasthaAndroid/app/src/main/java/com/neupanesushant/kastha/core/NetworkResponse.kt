package com.neupanesushant.kastha.core

sealed class Response<out T> {
    data class Success<T>(val responseData: T) : Response<T>()
    data class Failure(val errorMessage: String) : Response<Nothing>()
}

class ResponseResolver<T>(
    private val response: Response<T>,
    private val onFailure: (String) -> Unit = {},
    private val onSuccess: (T) -> Unit
) {
    init {
        if (response is Response.Success) {
            onSuccess(response.responseData)
        }
        if (response is Response.Failure) {
            onFailure(response.errorMessage)
        }
    }
}

