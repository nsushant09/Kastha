package com.neupanesushant.kastha.data.repo

import com.neupanesushant.kastha.domain.model.CartProduct

interface CartRepo {
    suspend fun add(productId: Int, userId: Int): List<CartProduct>
    suspend fun remove(cartProductIds: List<Int>): List<CartProduct>
    suspend fun all(userId: Int): List<CartProduct>

    suspend fun increment(cartProductId: Int): CartProduct
    suspend fun decrement(cartProductId: Int): CartProduct
}