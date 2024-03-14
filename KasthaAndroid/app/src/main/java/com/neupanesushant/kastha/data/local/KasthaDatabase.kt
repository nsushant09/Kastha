package com.neupanesushant.kastha.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.neupanesushant.kastha.domain.model.CartProduct
import com.neupanesushant.kastha.domain.model.Category
import com.neupanesushant.kastha.domain.model.Favourite
import com.neupanesushant.kastha.domain.model.Image
import com.neupanesushant.kastha.domain.model.ObjectModel
import com.neupanesushant.kastha.domain.model.Product

@Database(
    entities = [CartProduct::class, Category::class, Favourite::class, Image::class, ObjectModel::class, Product::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomConvertors::class)
abstract class KasthaDatabase : RoomDatabase() {
    abstract val cartProductDao: CartProductDao
    abstract val categoryDao: CategoryDao
    abstract val favouriteDao: FavouriteDao
    abstract val productDao: ProductDao
}