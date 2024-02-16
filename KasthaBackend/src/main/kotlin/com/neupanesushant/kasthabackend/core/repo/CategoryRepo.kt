package com.neupanesushant.kasthabackend.core.repo

import com.neupanesushant.kasthabackend.data.model.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepo : JpaRepository<Category, Int> {
}