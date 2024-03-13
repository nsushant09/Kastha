package com.neupanesushant.kastha.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeHandler(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): Response<T> = withContext(dispatcher) {
    return@withContext try {
        Response.Success(apiCall.invoke())
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> Response.Failure("Network Error")
            is HttpException -> Response.Failure(
                throwable.message()
            )

            else -> Response.Failure("Unknown Error")
        }
    }
}

suspend fun <T> safeHandler(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    value: T
) = withContext(dispatcher) {
    return@withContext Response.Success(value)
}