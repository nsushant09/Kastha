package com.neupanesushant.kastha.data.repo

import com.neupanesushant.kastha.domain.model.Review
import com.neupanesushant.kastha.domain.model.ReviewResponse

interface ReviewRepo {
    suspend fun add(productId: Int, userId: Int, review: Review): Review
    suspend fun getReviewOf(productId: Int): List<ReviewResponse>
}