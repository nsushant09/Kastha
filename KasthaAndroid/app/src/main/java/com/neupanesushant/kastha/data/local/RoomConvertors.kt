package com.neupanesushant.kastha.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neupanesushant.kastha.domain.model.Image
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.domain.model.Role

class RoomConvertors {

    @TypeConverter
    fun toJson(obj: Collection<Any>): String? {
        return Gson().toJson(obj)
    }

    @TypeConverter
    fun fromJsonCollection(obj: String): Collection<Any> {
        return Gson().fromJson(
            obj,
            object : TypeToken<Collection<Any>>() {}.type
        )
    }

    @TypeConverter
    fun fromJson(obj: String): Any {
        return Gson().fromJson(
            obj,
            object : TypeToken<Any>() {}.type
        )
    }

    @TypeConverter
    fun toJson(obj: Any): String? {
        return Gson().toJson(obj)
    }

    @TypeConverter
    fun fromImagesList(images: List<Image>): String {
        return Gson().toJson(images)
    }

    @TypeConverter
    fun toImagesList(imagesString: String): List<Image> {
        val type = object : TypeToken<List<Image>>() {}.type
        return Gson().fromJson(imagesString, type)
    }

    @TypeConverter
    fun fromProductSet(products: Set<Product>): String {
        return Gson().toJson(products)
    }

    @TypeConverter
    fun toProductSet(productsString: String): Set<Product> {
        val type = object : TypeToken<Set<Product>>() {}.type
        return Gson().fromJson(productsString, type)
    }

    @TypeConverter
    fun fromRolesList(roles: List<Role>): String {
        return Gson().toJson(roles)
    }

    @TypeConverter
    fun toRolesList(rolesString: String): List<Role> {
        val type = object : TypeToken<List<Role>>() {}.type
        return Gson().fromJson(rolesString, type)
    }
}