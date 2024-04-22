package com.neupanesushant.kastha.data.repo

import com.neupanesushant.kastha.core.Response
import com.neupanesushant.kastha.domain.model.CartProduct

interface CartRepo {
    suspend fun add(productId: Int, userId: Int): Response<List<CartProduct>>
    suspend fun remove(cartProductIds: List<Int>): Response<List<CartProduct>>
    suspend fun all(userId: Int): Response<List<CartProduct>>

    suspend fun increment(cartProductId: Int): Response<CartProduct>
    suspend fun decrement(cartProductId: Int): Response<CartProduct>
}