package com.neupanesushant.kastha.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val alignment: Alignment
) : Parcelable
