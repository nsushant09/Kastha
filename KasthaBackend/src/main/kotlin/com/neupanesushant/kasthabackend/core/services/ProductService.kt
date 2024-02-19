package com.neupanesushant.kasthabackend.core.services

import com.neupanesushant.kasthabackend.core.repo.ProductRepo
import com.neupanesushant.kasthabackend.data.model.Category
import com.neupanesushant.kasthabackend.data.model.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductService(
    @Autowired private val productRepo: ProductRepo
) {

    fun insert(product: Product) = productRepo.save(product)
    fun update(product: Product) = productRepo.save(product)
    fun delete(product: Product) = productRepo.delete(product)

    val all
        get() : Collection<Product> {
            return productRepo.findAll()
        }

    fun byId(id: Int): Product? {
        val product = productRepo.findById(id)
        return product.orElse(null)
    }

    fun bySearch(value: String): Collection<Product> {
        val products = productRepo.findByProductNameOrCategoryName(value)
        return products
    }

    fun byCategory(category: Category): Collection<Product> {
        val products = productRepo.findByCategory(category)
        return products
    }
}