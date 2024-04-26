package com.neupanesushant.kastha.domain.model

import java.io.Serializable
import java.sql.Date

data class Review(
    val id: Int,
    val description: String,
    val rating: Int,
    val date: Date
) : Serializable