package com.neupanesushant.kastha.domain.model.dto

data class BaseResponse<T>(
    val success: Boolean,
    val data: T
)
