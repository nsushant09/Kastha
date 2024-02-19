package com.neupanesushant.kasthabackend.data.dtomodel

import com.neupanesushant.kasthabackend.data.model.Product

data class CartProductDTO(
    val id: Int,
    val product: Product,
    val quantity: Int
)