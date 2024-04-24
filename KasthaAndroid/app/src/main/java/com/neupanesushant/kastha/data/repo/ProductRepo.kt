package com.neupanesushant.kastha.data.repo

import com.neupanesushant.kastha.core.Response
import com.neupanesushant.kastha.domain.model.Product

interface ProductRepo {
    suspend fun add(product: Product): Response<Product>
    suspend fun update(product: Product): Product

    suspend fun getProductOfId(id: Int): Product
    suspend fun getProductsOfCategory(categoryId: Int): List<Product>
    suspend fun getProductsBySearch(value: String): List<Product>
    suspend fun all(): Response<List<Product>>
}