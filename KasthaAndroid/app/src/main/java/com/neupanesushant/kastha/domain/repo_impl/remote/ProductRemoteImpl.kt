package com.neupanesushant.kastha.domain.repo_impl.remote

import com.neupanesushant.kastha.data.remote.Endpoint
import com.neupanesushant.kastha.data.repo.ProductRepo
import com.neupanesushant.kastha.domain.model.Product

class ProductRemoteImpl(
    private val endpoint: Endpoint
) : ProductRepo {
    override suspend fun add(product: Product): Product = endpoint.addProduct(product)

    override suspend fun update(product: Product): Product = endpoint.updateProduct(product)

    override suspend fun getProductOfId(id: Int): Product = endpoint.getProductById(id)

    override suspend fun getProductsOfCategory(categoryId: Int): List<Product> =
        endpoint.getProductsByCategory(categoryId)

    override suspend fun getProductsBySearch(value: String): List<Product> =
        endpoint.getProductBySearch(value)

    override suspend fun all(): List<Product> = endpoint.products
}