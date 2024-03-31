package com.neupanesushant.kastha.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> requestHandler(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    value: T
) = withContext(dispatcher) {
    return@withContext Response.Success(value)
}

suspend fun <T> requestHandler(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> retrofit2.Response<T>
): Response<T> = withContext(dispatcher) {
    return@withContext try {
        val apiResponse = apiCall.invoke()
        if (apiResponse.isSuccessful) {
            val responseBody = apiResponse.body()
            if (responseBody != null) {
                Response.Success(responseBody)
            } else {
                Response.Failure("Response body is null")
            }
        } else {
            Response.Failure(apiResponse.errorBody()?.string() ?: "Unknown error")
        }
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> Response.Failure("Network Error")
            is HttpException -> Response.Failure("HTTP Error: ${throwable.message()}")
            else -> Response.Failure("Unknown Error")
        }
    }
}