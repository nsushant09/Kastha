package com.neupanesushant.kastha.domain.model

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: UserRole
)