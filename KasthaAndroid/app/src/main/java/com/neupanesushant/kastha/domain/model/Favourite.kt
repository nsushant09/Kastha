package com.neupanesushant.kastha.domain.model

data class Favourite(
    val id: Int,
    val userId: Int,
    val products: Set<Product>
)
