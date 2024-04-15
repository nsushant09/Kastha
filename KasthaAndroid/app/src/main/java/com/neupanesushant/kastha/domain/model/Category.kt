package com.neupanesushant.kastha.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.neupanesushant.kastha.extra.RoomConstants
import kotlinx.parcelize.Parcelize

@Entity(RoomConstants.CATEGORY)
@Parcelize
data class Category(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "category_id")
    val id: Int,
    @ColumnInfo(name = "category_name")
    val name: String,
    val imageUrl: String,
    @Embedded
    val alignment: Alignment
) : Parcelable
