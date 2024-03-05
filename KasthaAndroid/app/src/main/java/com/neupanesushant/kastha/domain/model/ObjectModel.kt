package com.neupanesushant.kastha.domain.model

import android.os.Parcelable
import androidx.room.Entity
import com.neupanesushant.kastha.extra.RoomConstants
import kotlinx.parcelize.Parcelize

@Entity(RoomConstants.OBJECT_MODEL)
@Parcelize
data class ObjectModel(
    val id: Int,
    val url: String
) : Parcelable