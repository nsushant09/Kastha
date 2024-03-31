package com.neupanesushant.kasthabackend.data.model

data class BaseResponse<T>(
    val success: Boolean,
    val data: T
)