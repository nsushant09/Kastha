package com.neupanesushant.kasthabackend.core.services

import com.neupanesushant.kasthabackend.core.repo.ProductRepo
import com.neupanesushant.kasthabackend.data.model.Category
import com.neupanesushant.kasthabackend.data.model.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

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

    fun recommended(
        category1: Optional<Category>,
        category2: Optional<Category>,
        category3: Optional<Category>
    ): Collection<Product> {
        val products = mutableSetOf<Product>()

        if (category1.isPresent) {
            val productsFromCategory1 = productRepo.findByCategory(category1.get())
            products.addAll(productsFromCategory1)
        }

        if (category2.isPresent) {
            val productsFromCategory2 = productRepo.findByCategory(category2.get())
            products.addAll(productsFromCategory2)
        }

        if (category3.isPresent) {
            val productsFromCategory3 = productRepo.findByCategory(category3.get())
            products.addAll(productsFromCategory3)
        }

        if (category1.isEmpty && category2.isEmpty && category3.isEmpty) {
            // TODO : Randomise and show the products
        }

        return products
    }

    fun latest(): Collection<Product> {
        return productRepo.findTop10ByOrderByIdDesc()
    }
}