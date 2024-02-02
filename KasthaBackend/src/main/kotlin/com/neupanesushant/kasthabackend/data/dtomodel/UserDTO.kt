package com.neupanesushant.kasthabackend.data.dtomodel

import javax.management.relation.Role

data class UserDTO(
    val id : Int,
    val firstName : String,
    val lastName : String,
    val email : String,
    val roles : List<Role>
)
