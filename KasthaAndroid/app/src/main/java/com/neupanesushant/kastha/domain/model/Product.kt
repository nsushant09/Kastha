package com.neupanesushant.kastha.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.neupanesushant.kastha.extra.RoomConstants
import kotlinx.parcelize.Parcelize

@Entity(RoomConstants.PRODUCT)
@Parcelize
data class Product(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val description: String,
    val price: Float,
    val category: Category,
    val images: List<Image>,
    val model: ObjectModel? = null
) : Parcelable
