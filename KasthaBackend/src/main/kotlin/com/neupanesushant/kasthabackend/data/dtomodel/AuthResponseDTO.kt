package com.neupanesushant.kasthabackend.data.dtomodel

data class AuthResponseDTO(
    val userId : Int,
    val accessToken : String,
    val tokenType : String = "Bearer "
)
