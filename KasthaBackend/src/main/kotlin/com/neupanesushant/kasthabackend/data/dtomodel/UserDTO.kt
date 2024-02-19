package com.neupanesushant.kasthabackend.data.dtomodel

import com.neupanesushant.kasthabackend.data.model.Role


data class UserDTO(
    val id : Int,
    val firstName : String,
    val lastName : String,
    val email : String,
    val roles : List<Role>
)
