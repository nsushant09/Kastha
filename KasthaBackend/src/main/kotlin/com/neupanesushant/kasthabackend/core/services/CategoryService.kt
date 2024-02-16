package com.neupanesushant.kasthabackend.core.services

import com.neupanesushant.kasthabackend.core.repo.CategoryRepo
import com.neupanesushant.kasthabackend.data.model.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategoryService(
    @Autowired private val categoryRepo: CategoryRepo
) {
    fun insert(category: Category) = categoryRepo.save(category)
    fun update(category: Category) = categoryRepo.save(category)
    fun delete(category: Category) = categoryRepo.delete(category)
    fun ofId(id: Int) : Optional<Category> = categoryRepo.findById(id)

    val all: List<Category>
        get() {
            return categoryRepo.findAll()
        }
}