package com.neupanesushant.kastha.domain.repo_impl.remote

import com.neupanesushant.kastha.core.requestHandler
import com.neupanesushant.kastha.data.remote.Endpoint
import com.neupanesushant.kastha.data.repo.FavoriteRepo
import com.neupanesushant.kastha.extra.Mapper

class FavoriteRemoteImpl(
    private val endpoint: Endpoint
) : FavoriteRepo {
    override suspend fun add(productId: Int, userId: Int) =
        requestHandler {
            endpoint.addProductToFavorite(productId, userId)
        }

    override suspend fun remove(productIds: List<Int>, userId: Int) =
        requestHandler {
            endpoint.removeProductFromFavorite(productIds, userId)
        }

    override suspend fun all(userId: Int) =
        requestHandler {
            endpoint.allFavoriteProducts(userId)
        }
}

