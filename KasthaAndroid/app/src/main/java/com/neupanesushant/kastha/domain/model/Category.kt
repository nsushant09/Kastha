package com.neupanesushant.kastha.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.neupanesushant.kastha.extra.RoomConstants
import kotlinx.parcelize.Parcelize

@Entity(RoomConstants.CATEGORY)
@Parcelize
data class Category(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val imageUrl: String,
    val alignment: Alignment
) : Parcelable
