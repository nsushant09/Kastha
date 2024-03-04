package com.neupanesushant.kastha.domain.repo_impl.remote

import com.neupanesushant.kastha.data.remote.Endpoint
import com.neupanesushant.kastha.data.repo.CartRepo
import com.neupanesushant.kastha.domain.model.CartProduct

class CartRemoteImpl(
    private val endpoint: Endpoint
) : CartRepo {
    override suspend fun add(productId: Int, userId: Int): List<CartProduct> =
        endpoint.addProductToCart(productId, userId)

    override suspend fun remove(cartProductId: Int): List<CartProduct> =
        endpoint.removeProductFromCart(cartProductId)

    override suspend fun all(userId: Int): List<CartProduct> = endpoint.allCartProducts(userId)
}