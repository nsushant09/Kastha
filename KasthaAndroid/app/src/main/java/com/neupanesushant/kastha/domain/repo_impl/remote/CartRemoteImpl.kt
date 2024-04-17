package com.neupanesushant.kastha.domain.repo_impl.remote

import com.neupanesushant.kastha.data.remote.Endpoint
import com.neupanesushant.kastha.data.repo.CartRepo
import com.neupanesushant.kastha.domain.model.CartProduct
import com.neupanesushant.kastha.extra.Mapper
import kotlinx.coroutines.flow.flow

class CartRemoteImpl(
    private val endpoint: Endpoint
) : CartRepo {
    override suspend fun add(productId: Int, userId: Int): CartProduct =
        Mapper.toBaseUrl(endpoint.addProductToCart(productId, userId))

    override suspend fun remove(cartProductIds: List<Int>) =
        endpoint.removeProductFromCart(cartProductIds)

    override suspend fun all(userId: Int) = flow {
        emit(endpoint.allCartProducts(userId).map(Mapper::toBaseUrl))
    }

    override suspend fun increment(cartProductId: Int) =
        Mapper.toBaseUrl(endpoint.increment(cartProductId))

    override suspend fun decrement(cartProductId: Int) =
        Mapper.toBaseUrl(endpoint.decrement(cartProductId))
}