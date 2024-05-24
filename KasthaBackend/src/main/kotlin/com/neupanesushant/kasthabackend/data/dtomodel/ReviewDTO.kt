package com.neupanesushant.kasthabackend.data.dtomodel

import java.sql.Date

data class ReviewDTO(
    val id: Int,
    val description: String,
    val rating: Int,
    val date: String,
    val userName : String
)