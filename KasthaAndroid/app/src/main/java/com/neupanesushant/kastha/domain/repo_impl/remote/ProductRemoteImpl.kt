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
    override suspend fun add(product: Product) =
        requestHandler { endpoint.addProduct(product) }

    override suspend fun update(
        product: Product,
        isModelChanged: Boolean,
        areImagesChanged: Boolean
    ) = requestHandler {
        endpoint.updateProduct(product, isModelChanged, areImagesChanged)
    }

    override suspend fun getProductOfId(id: Int): Product =
        Mapper.toBaseUrl(endpoint.getProductById(id))

    override suspend fun getProductsOfCategory(categoryId: Int): List<Product> =
        endpoint.getProductsByCategory(categoryId)

    override suspend fun getProductsBySearch(value: String): List<Product> =
        endpoint.getProductBySearch(value)

    override suspend fun getProductByRecommended(categoryIds: List<Int>) = requestHandler {
        endpoint.getProductByRecommended(categoryIds)
    }

    override suspend fun all() =
        requestHandler {
            if (allProducts.isEmpty()) {
                endpoint.getProducts()
            } else {
                Response.success(allProducts)
            }
        }

    override fun cacheAllProducts(products: List<Product>) {
        allProducts = products
    }
}


