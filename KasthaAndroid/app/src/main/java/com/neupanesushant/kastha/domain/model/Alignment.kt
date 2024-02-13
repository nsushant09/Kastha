package com.neupanesushant.kastha.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Alignment(
    val id: Int,
    val name: String
) : Parcelable
