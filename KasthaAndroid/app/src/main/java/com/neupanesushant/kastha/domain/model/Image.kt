package com.neupanesushant.kastha.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.neupanesushant.kastha.extra.RoomConstants
import kotlinx.parcelize.Parcelize

@Entity(RoomConstants.IMAGE)
@Parcelize
data class Image(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "image_id")
    val id: Int,
    val url: String
) : Parcelable
