package com.neupanesushant.kastha.domain.repo_impl.remote

import com.neupanesushant.kastha.data.remote.Endpoint
import com.neupanesushant.kastha.data.repo.FavoriteRepo
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.extra.Mapper

class FavoriteRemoteImpl(
    private val endpoint: Endpoint
) : FavoriteRepo {
    override suspend fun add(productId: Int, userId: Int): List<Product> =
        endpoint.addProductToFavorite(productId, userId).map(Mapper::toBaseUrl)

    override suspend fun remove(productIds: List<Int>, userId: Int): List<Product> =
        endpoint.removeProductFromFavorite(productIds, userId).map(Mapper::toBaseUrl)

    override suspend fun all(userId: Int): List<Product> = endpoint.allFavoriteProducts(userId).map(Mapper::toBaseUrl)
}