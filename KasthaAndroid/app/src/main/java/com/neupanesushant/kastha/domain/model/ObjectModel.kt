package com.neupanesushant.kastha.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ObjectModel(
    val id: Int,
    val url: String
) : Parcelable