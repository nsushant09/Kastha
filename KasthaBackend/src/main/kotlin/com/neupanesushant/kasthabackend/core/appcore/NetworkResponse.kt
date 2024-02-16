package com.neupanesushant.kasthabackend.core.appcore

sealed class NetworkResponse<out T> {
    data class Success<T>(val responseData: T) : NetworkResponse<T>()
    data class Failure(val errorMessage: String) : NetworkResponse<Nothing>()
}

class NetworkResponseResolver<T>(
    private val response: NetworkResponse<T>,
    private val onSuccess: (T) -> Unit,
    private val onFailure: (String) -> Unit = {}
) {
    operator fun invoke() {
        if (response is NetworkResponse.Success) {
            onSuccess(response.responseData)
            return
        }
        if (response is NetworkResponse.Failure) {
            onFailure(response.errorMessage)
            return
        }
    }
}
