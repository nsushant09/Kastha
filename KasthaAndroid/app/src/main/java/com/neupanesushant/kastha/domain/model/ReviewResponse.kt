package com.neupanesushant.kastha.domain.model

import java.util.Date

data class ReviewResponse(
    val id: Int,
    val description: String,
    val rating: Int,
    val date: Date,
    val userName: String? = null
)
