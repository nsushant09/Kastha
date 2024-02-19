package com.neupanesushant.kasthabackend.core.services

import com.neupanesushant.kasthabackend.core.repo.ProductRepo
import com.neupanesushant.kasthabackend.core.repo.ReviewRepo
import com.neupanesushant.kasthabackend.core.repo.UserRepo
import com.neupanesushant.kasthabackend.data.model.Review
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ReviewService(
    @Autowired private val reviewRepo: ReviewRepo,
    @Autowired private val userRepo: UserRepo,
    @Autowired private val productService: ProductService
) {
    fun insert(review: Review, userId: Int, productId: Int): Review? {
        val user = userRepo.findById(userId).orElse(null) ?: return null
        val product = productService.byId(productId) ?: return null
        review.user = user
        review.product = product
        return reviewRepo.save(review)
    }

    fun byId(productId: Int) = reviewRepo.findByProductId(productId)
}