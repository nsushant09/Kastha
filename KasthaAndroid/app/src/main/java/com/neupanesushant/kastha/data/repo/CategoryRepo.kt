package com.neupanesushant.kastha.data.repo

import com.neupanesushant.kastha.domain.model.Category

interface CategoryRepo {
    suspend fun getCategories(): List<Category>
    suspend fun getCategoryOf(id: Int): Category
}