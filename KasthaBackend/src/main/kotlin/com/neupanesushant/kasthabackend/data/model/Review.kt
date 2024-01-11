package com.neupanesushant.kasthabackend.data.model

import java.sql.Date

data class Review(
    val id : Int,
    val description : Int,
    val rating : Int,
    val date : Date,
    val product: Product,
    val user : User
)