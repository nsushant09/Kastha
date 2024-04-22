package com.neupanesushant.kastha.domain.repo_impl.remote

import com.neupanesushant.kastha.core.requestHandler
import com.neupanesushant.kastha.data.remote.Endpoint
import com.neupanesushant.kastha.data.repo.ReviewRepo
import com.neupanesushant.kastha.domain.model.Review
import com.neupanesushant.kastha.domain.model.ReviewResponse

class ReviewRemoteImpl(
    private val endpoint: Endpoint
) : ReviewRepo {
    override suspend fun add(productId: Int, userId: Int, review: Review)=
        requestHandler { endpoint.addReview(productId, userId, review) }

    override suspend fun getReviewOf(productId: Int) =
        requestHandler { endpoint.getReviewsOf(productId) }
}