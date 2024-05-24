package com.neupanesushant.kasthabackend.data.dtomodel

import com.neupanesushant.kasthabackend.data.model.Role

data class UserUpdateDTO(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val location: String,
    val gender: String,
    val roles: List<Role>
)