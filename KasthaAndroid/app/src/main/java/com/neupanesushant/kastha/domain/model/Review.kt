package com.neupanesushant.kastha.domain.model

import java.io.Serializable

data class Review(
    val id: Int,
    val description: String,
    val rating: Int,
    val date: String
) : Serializable