package com.neupanesushant.kastha.domain.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.neupanesushant.kastha.extra.RoomConstants
import kotlinx.parcelize.Parcelize


@Entity(tableName = RoomConstants.CART)
@Parcelize
data class CartProduct(
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @Embedded
    val product: Product,
    val quantity: Int
) : Parcelable