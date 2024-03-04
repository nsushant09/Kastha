package com.neupanesushant.kastha.domain.usecase

import com.neupanesushant.kastha.data.repo.CartRepo
import com.neupanesushant.kastha.domain.model.CartProduct
import kotlinx.coroutines.flow.MutableSharedFlow

class CartUseCase(
    private val cartRepo: CartRepo
) {
    val cartProducts = MutableSharedFlow<List<CartProduct>>()

    suspend fun add(productId: Int, userId: Int) {
        val response = cartRepo.add(productId, userId)
        cartProducts.emit(response)
    }

    suspend fun remove(cartProductId: Int) {
        val response = cartRepo.remove(cartProductId)
        cartProducts.emit(response)
    }

    suspend fun all(userId: Int) {
        val response = cartRepo.all(userId)
        cartProducts.emit(response)
    }
}