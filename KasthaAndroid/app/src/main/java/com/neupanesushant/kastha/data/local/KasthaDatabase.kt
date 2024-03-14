package com.neupanesushant.kastha.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomConvertors::class)
abstract class KasthaDatabase : RoomDatabase() {

}