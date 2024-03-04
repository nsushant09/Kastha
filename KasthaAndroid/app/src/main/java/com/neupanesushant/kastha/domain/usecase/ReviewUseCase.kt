package com.neupanesushant.kastha.domain.usecase

import com.neupanesushant.kastha.data.repo.ReviewRepo
import com.neupanesushant.kastha.domain.model.Review

class ReviewUseCase(
    private val reviewRepo: ReviewRepo
) {
    suspend fun add(productId: Int, userId: Int, review: Review) =
        reviewRepo.add(productId, userId, review)

    suspend fun getReviewOf(productId: Int) = reviewRepo.getReviewOf(productId)
}