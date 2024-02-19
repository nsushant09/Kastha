package com.neupanesushant.kastha.domain.model

data class Cart(
    val id: Int,
    val userId: Int,
    val products: Set<CartProduct>
)
