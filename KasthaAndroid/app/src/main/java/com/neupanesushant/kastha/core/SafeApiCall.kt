package com.neupanesushant.kastha.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): NetworkResponse<T> = withContext(dispatcher) {
    return@withContext try {
        NetworkResponse.Success(apiCall.invoke())
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> NetworkResponse.Failure("Network Error")
            is HttpException -> NetworkResponse.Failure(
                throwable.message()
            )

            else -> NetworkResponse.Failure("Unknown Error")
        }
    }
}