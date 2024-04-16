package com.neupanesushant.kastha.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.neupanesushant.kastha.BuildConfig
import com.neupanesushant.kastha.extra.RoomConstants
import kotlinx.parcelize.Parcelize

@Entity(RoomConstants.PRODUCT)
@Parcelize
data class Product(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "product_id")
    val id: Int,
    @ColumnInfo(name = "product_name")
    val name: String,
    val description: String,
    val price: Float,
    @Embedded
    val category: Category,
    val images: List<Image>,
    @Embedded
    val model: ObjectModel? = null
) : Parcelable