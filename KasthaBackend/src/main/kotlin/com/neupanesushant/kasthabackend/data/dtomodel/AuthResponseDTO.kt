package com.neupanesushant.kasthabackend.data.dtomodel

data class AuthResponseDTO(
    val accessToken : String,
    val tokenType : String = "Bearer "
)
