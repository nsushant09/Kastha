package com.neupanesushant.kastha.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.neupanesushant.kastha.extra.RoomConstants
import kotlinx.parcelize.Parcelize

@Entity(tableName = RoomConstants.FAVORITE)
@Parcelize
data class Favourite(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val userId: Int,
    val products: Set<Product>
) : Parcelable
