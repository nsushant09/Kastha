package com.neupanesushant.kastha.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.neupanesushant.kastha.extra.RoomConstants
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = RoomConstants.USER
)
data class User(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "user_id")
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val gender: String,
    val location: String,
    val roles: List<Role>
) : Parcelable