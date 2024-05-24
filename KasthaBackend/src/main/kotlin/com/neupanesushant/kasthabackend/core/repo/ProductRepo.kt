package com.neupanesushant.kasthabackend.core.repo

import com.neupanesushant.kasthabackend.data.model.Category
import com.neupanesushant.kasthabackend.data.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ProductRepo : JpaRepository<Product, Int> {
    fun findByCategory(category: Category): List<Product>

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:searchValue% OR p.category.name LIKE %:searchValue%")
    fun findByProductNameOrCategoryName(@Param("searchValue") searchValue: String): Set<Product>

    fun findTop10ByOrderByIdDesc(): List<Product>
}