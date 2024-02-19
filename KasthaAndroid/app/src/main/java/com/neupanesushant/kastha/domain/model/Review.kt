package com.neupanesushant.kastha.domain.model

import java.util.Date

data class Review(
    val id: Int,
    val description: String,
    val rating: Int,
    val date: Date,
)
