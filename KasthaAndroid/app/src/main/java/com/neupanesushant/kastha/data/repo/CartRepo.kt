package com.neupanesushant.kastha.data.repo

import com.neupanesushant.kastha.domain.model.CartProduct
import kotlinx.coroutines.flow.Flow

interface CartRepo {
    suspend fun add(productId: Int, userId: Int): CartProduct
    suspend fun remove(cartProductId: Int)
    suspend fun all(userId: Int): Flow<List<CartProduct>>

    suspend fun increment(cartProductId: Int): CartProduct
    suspend fun decrement(cartProductId: Int): CartProduct
}