package com.neupanesushant.kasthabackend.core.repo

import com.neupanesushant.kasthabackend.data.model.Review
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepo : JpaRepository<Review, Int> {
    fun findByProductId(productId: Int): Set<Review>
}