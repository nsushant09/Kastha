package com.neupanesushant.kastha.domain.repo_impl.remote

import com.neupanesushant.kastha.core.requestHandler
import com.neupanesushant.kastha.data.remote.Endpoint
import com.neupanesushant.kastha.data.repo.ProductRepo
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.extra.Mapper
import retrofit2.Response

class ProductRemoteImpl(
    private val endpoint: Endpoint
) : ProductRepo {

    private var allProducts: List<Product> = emptyList()
    override suspend fun add(product: Product): Product =
        Mapper.toBaseUrl(endpoint.addProduct(product))

    override suspend fun update(product: Product): Product =
        Mapper.toBaseUrl(endpoint.updateProduct(product))

    override suspend fun getProductOfId(id: Int): Product =
        Mapper.toBaseUrl(endpoint.getProductById(id))

    override suspend fun getProductsOfCategory(categoryId: Int): List<Product> =
        endpoint.getProductsByCategory(categoryId).map(Mapper::toBaseUrl)

    override suspend fun getProductsBySearch(value: String): List<Product> =
        endpoint.getProductBySearch(value).map(Mapper::toBaseUrl)

    override suspend fun all() =
        requestHandler {
            allProducts.ifEmpty { allProducts = endpoint.getProducts().map(Mapper::toBaseUrl) }
            Response.success(allProducts)
        }
}


