package com.neupanesushant.kastha.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.neupanesushant.kastha.extra.RoomConstants
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = RoomConstants.FAVORITE,
    indices = [Index(value = ["product_id"], unique = true)]
)
@Parcelize
data class FavouriteProduct(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "favourite_product_id")
    val id: Int = 1,

    @Embedded
    val product: Product
) : Parcelable
