package com.neupanesushant.kastha.domain.repo_impl.remote

import com.neupanesushant.kastha.data.remote.Endpoint
import com.neupanesushant.kastha.data.repo.FavoriteRepo
import com.neupanesushant.kastha.domain.model.Product

class FavoriteRemoteImpl(
    private val endpoint: Endpoint
) : FavoriteRepo {
    override suspend fun add(productId: Int, userId: Int): List<Product> =
        endpoint.addProductToFavorite(productId, userId)

    override suspend fun remove(productId: Int, userId: Int): List<Product> =
        endpoint.removeProductFromFavorite(productId, userId)

    override suspend fun all(userId: Int): List<Product> = endpoint.allFavoriteProducts(userId)
}