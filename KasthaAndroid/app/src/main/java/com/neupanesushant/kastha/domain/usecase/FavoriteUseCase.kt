package com.neupanesushant.kastha.domain.usecase

import com.neupanesushant.kastha.data.repo.FavoriteRepo
import com.neupanesushant.kastha.domain.model.Product
import kotlinx.coroutines.flow.MutableSharedFlow

class FavoriteUseCase(
    private val favoriteRepo: FavoriteRepo
) {
    val favorites = MutableSharedFlow<List<Product>>()

    suspend fun add(productId: Int, userId: Int) {
        val response = favoriteRepo.add(productId, userId)
        favorites.emit(response)
    }

    suspend fun remove(productId: Int, userId: Int) {
        val response = favoriteRepo.remove(productId, userId)
        favorites.emit(response)
    }

    suspend fun all(userId: Int) {
        val response = favoriteRepo.all(userId)
        favorites.emit(response)
    }
}