package com.neupanesushant.kastha.data.repo

import com.neupanesushant.kastha.domain.model.CartProduct

interface CartRepo {
    suspend fun add(productId: Int, userId: Int): List<CartProduct>
    suspend fun remove(cartProductId: Int): List<CartProduct>
    suspend fun all(userId: Int): List<CartProduct>
}