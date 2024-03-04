package com.neupanesushant.kastha.domain.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterDTO(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val location : String,
    val gender : String
) : Parcelable
