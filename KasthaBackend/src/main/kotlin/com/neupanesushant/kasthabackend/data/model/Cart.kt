package com.neupanesushant.kasthabackend.data.model

data class Cart(
    val id : Int,
    val user : User,
    val products : Set<Product>
)
