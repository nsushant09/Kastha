package com.neupanesushant.kasthabackend.core.services

import com.neupanesushant.kasthabackend.core.repo.ProductRepo
import com.neupanesushant.kasthabackend.data.model.Category
import com.neupanesushant.kasthabackend.data.model.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.min

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
        categories: List<Category>
    ): Collection<Product> {
        val products = mutableSetOf<Product>()

        categories.forEach {
            val categoryProducts = productRepo.findByCategory(it)
            products.addAll(categoryProducts.subList(0, min(categoryProducts.size, 4)))
        }

        if (products.isEmpty()) {
            val categoryIdWithCountMap = HashMap<Int, Int>()

            val filteredProducts = mutableSetOf<Product>()
            all.forEach { product ->
                if (categoryIdWithCountMap.containsKey(product.category.id)) {
                    if (categoryIdWithCountMap[product.category.id]!! < 2) {
                        filteredProducts.add(product)
                        categoryIdWithCountMap[product.category.id] =
                            categoryIdWithCountMap[product.category.id]!! + 1
                    }
                } else {
                    filteredProducts.add(product)
                    categoryIdWithCountMap[product.category.id] = 1
                }
            }
            products.addAll(filteredProducts)
        }

        return products
    }

    fun latest(): Collection<Product> {
        return productRepo.findTop10ByOrderByIdDesc()
    }
}