package com.neupanesushant.kasthabackend.data.model

data class Favorite (
    val id : Int,
    val user : User,
    val products : Set<Product>
)
