package com.neupanesushant.kastha.domain.usecase

import com.neupanesushant.kastha.data.repo.ProductRepo
import com.neupanesushant.kastha.domain.model.Product

class ProductUseCase(
    private val productRepo: ProductRepo
) {
    suspend fun add(product: Product) = productRepo.add(product)
    suspend fun update(product: Product) = productRepo.update(product)
    suspend fun getProductOfId(id: Int) = productRepo.getProductOfId(id)
    suspend fun getProductsOfCategory(categoryId: Int) =
        productRepo.getProductsOfCategory(categoryId)

    suspend fun getProductsBySearch(value: String) = productRepo.getProductsBySearch(value)
    suspend fun all() = productRepo.all()

}