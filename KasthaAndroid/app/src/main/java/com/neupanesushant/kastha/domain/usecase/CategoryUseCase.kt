package com.neupanesushant.kastha.domain.usecase

import com.neupanesushant.kastha.data.repo.CategoryRepo
import com.neupanesushant.kastha.domain.model.Category

class CategoryUseCase(
    private val categoryRepo: CategoryRepo
) {
    private val categories: List<Category>? = null
    suspend fun getCategories(): List<Category> {
        return categories ?: categoryRepo.getCategories()
    }

    suspend fun getCategoryOfId(id: Int): Category {
        val category = categories?.find { it.id == id }
        return category ?: categoryRepo.getCategoryOf(id)
    }
}