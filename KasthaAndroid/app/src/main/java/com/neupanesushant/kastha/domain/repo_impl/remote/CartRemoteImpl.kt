package com.neupanesushant.kastha.domain.repo_impl.remote

import com.neupanesushant.kastha.core.requestHandler
import com.neupanesushant.kastha.data.remote.Endpoint
import com.neupanesushant.kastha.data.repo.CartRepo
import com.neupanesushant.kastha.extra.Mapper

class CartRemoteImpl(
    private val endpoint: Endpoint
) : CartRepo {
    override suspend fun add(productId: Int, userId: Int) =
        requestHandler {
            endpoint.addProductToCart(productId, userId).also {
                it.body()?.map(Mapper::toBaseUrl)
            }
        }

    override suspend fun remove(cartProductIds: List<Int>) =
        requestHandler {
            endpoint.removeProductFromCart(cartProductIds).also {
                it.body()?.map(Mapper::toBaseUrl)
            }
        }

    override suspend fun all(userId: Int) =
        requestHandler {
            endpoint.allCartProducts(userId).also {
                it.body()?.map(Mapper::toBaseUrl)
            }
        }


    override suspend fun increment(cartProductId: Int) =
        requestHandler {
            endpoint.increment(cartProductId).also {
                it.body()?.also { Mapper.toBaseUrl(it) }
            }
        }

    override suspend fun decrement(cartProductId: Int) =
        requestHandler {
            endpoint.decrement(cartProductId).also {
                it.body()?.also { Mapper.toBaseUrl(it) }
            }
        }
}