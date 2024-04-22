package com.neupanesushant.kastha.data.repo

import com.neupanesushant.kastha.core.Response
import com.neupanesushant.kastha.domain.model.Product

interface FavoriteRepo {
    suspend fun add(productId: Int, userId: Int): Response<List<Product>>
    suspend fun remove(productIds: List<Int>, userId: Int): Response<List<Product>>
    suspend fun all(userId: Int): Response<List<Product>>

}