package com.neupanesushant.kastha.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomConvertors {
    companion object {

        @TypeConverter
        @JvmStatic
        fun toJson(obj: List<Any>): String? {
            return Gson().toJson(obj)
        }

        @TypeConverter
        @JvmStatic
        fun fromJsonListTask(obj: String): List<Any> {
            return Gson().fromJson(
                obj,
                object : TypeToken<List<Any>>() {}.type
            )
        }

        @TypeConverter
        @JvmStatic
        fun toJson(obj: Any): String? {
            return Gson().toJson(obj)
        }

        @TypeConverter
        @JvmStatic
        fun fromJson(obj: String): Any {
            return Gson().fromJson(
                obj,
                object : TypeToken<Any>() {}.type
            )
        }
    }
}