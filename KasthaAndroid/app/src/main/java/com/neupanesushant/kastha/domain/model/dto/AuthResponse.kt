package com.neupanesushant.kastha.domain.model.dto

data class AuthResponse(
    val userId : Int,
    val accessToken: String,
    val tokenType: String
)
