package com.neupanesushant.kastha.domain.model

data class OTPMailResponse(
    val authenticationKey: String,
    val success: Boolean,
)