package com.neupanesushant.kastha.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.neupanesushant.kastha.domain.model.CartProduct
import com.neupanesushant.kastha.domain.model.Category
import com.neupanesushant.kastha.domain.model.FavouriteProduct
import com.neupanesushant.kastha.domain.model.Image
import com.neupanesushant.kastha.domain.model.ObjectModel
import com.neupanesushant.kastha.domain.model.Product

@Database(
    entities = [CartProduct::class, Category::class, FavouriteProduct::class, Image::class, ObjectModel::class, Product::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomConvertors::class)
abstract class KasthaDatabase : RoomDatabase() {
    abstract fun cartProductDao(): CartProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun favouriteDao(): FavouriteDao
    abstract fun productDao(): ProductDao
}