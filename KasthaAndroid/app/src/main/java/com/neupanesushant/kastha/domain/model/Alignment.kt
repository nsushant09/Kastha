package com.neupanesushant.kastha.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class Alignment(
    @ColumnInfo(name = "alignment_id")
    val id: Int,
    @ColumnInfo(name = "alignment_name")
    val name: String
) : Parcelable
