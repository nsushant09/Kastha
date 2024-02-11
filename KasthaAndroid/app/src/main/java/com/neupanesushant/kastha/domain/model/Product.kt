package com.neupanesushant.kastha.domain.model

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Float,
    val images: List<Image>,
    val model: ObjectModel? = null
)
