package com.neupanesushant.kastha.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.neupanesushant.kastha.extra.RoomConstants
import kotlinx.parcelize.Parcelize

@Entity(tableName = RoomConstants.FAVORITE)
@Parcelize
data class Favourite(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "favourite_id")
    val id: Int = 1,
    val userId: Int,
    val products: Set<Product>
) : Parcelable
