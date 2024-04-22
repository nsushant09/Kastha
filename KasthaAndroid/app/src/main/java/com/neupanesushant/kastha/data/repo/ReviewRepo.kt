package com.neupanesushant.kastha.data.repo

import com.neupanesushant.kastha.core.Response
import com.neupanesushant.kastha.domain.model.Review
import com.neupanesushant.kastha.domain.model.ReviewResponse

interface ReviewRepo {
    suspend fun add(productId: Int, userId: Int, review: Review): Response<Review>
    suspend fun getReviewOf(productId: Int): Response<List<ReviewResponse>>
}