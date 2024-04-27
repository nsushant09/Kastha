package com.neupanesushant.kastha.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Role(
    val id: Int,
    val name: String
) : Parcelable