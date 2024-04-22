package com.neupanesushant.kastha.domain.repo_impl.remote

import com.neupanesushant.kastha.core.requestHandler
import com.neupanesushant.kastha.data.remote.Endpoint
import com.neupanesushant.kastha.data.repo.CartRepo

class CartRemoteImpl(
    private val endpoint: Endpoint
) : CartRepo {
    override suspend fun add(productId: Int, userId: Int) =
        requestHandler {
            endpoint.addProductToCart(productId, userId)
        }

    override suspend fun remove(cartProductIds: List<Int>) =
        requestHandler {
            endpoint.removeProductFromCart(cartProductIds)
        }

    override suspend fun all(userId: Int) =
        requestHandler {
            endpoint.allCartProducts(userId)
        }


    override suspend fun increment(cartProductId: Int) =
        requestHandler {
            endpoint.increment(cartProductId)
        }

    override suspend fun decrement(cartProductId: Int) =
        requestHandler {
            endpoint.decrement(cartProductId)
        }
}