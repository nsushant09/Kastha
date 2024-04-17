package com.neupanesushant.kastha.data.repo

import com.neupanesushant.kastha.domain.model.Product

interface FavoriteRepo {
    suspend fun add(productId: Int, userId: Int): List<Product>
    suspend fun remove(productIds: List<Int>, userId: Int): List<Product>
    suspend fun all(userId: Int): List<Product>

}