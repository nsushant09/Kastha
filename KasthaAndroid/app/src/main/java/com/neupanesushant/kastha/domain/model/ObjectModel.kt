package com.neupanesushant.kastha.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.neupanesushant.kastha.extra.RoomConstants
import kotlinx.parcelize.Parcelize

@Entity(RoomConstants.OBJECT_MODEL)
@Parcelize
data class ObjectModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "model_id")
    val id: Int,
    val url: String
) : Parcelable