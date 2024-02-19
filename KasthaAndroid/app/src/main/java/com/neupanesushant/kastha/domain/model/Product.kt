package com.neupanesushant.kastha.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Float,
    val category: Category,
    val images: List<Image>,
    val model: ObjectModel? = null
) : Parcelable
