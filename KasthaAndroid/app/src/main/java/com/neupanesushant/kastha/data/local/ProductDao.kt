package com.neupanesushant.kastha.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.neupanesushant.kastha.domain.model.Category
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.extra.RoomConstants

@Dao
interface ProductDao {
    @Insert
    suspend fun insert(product: Product)

    @Update
    suspend fun update(product: Product)

    @Query("SELECT * FROM ${RoomConstants.PRODUCT} WHERE product_id = :id")
    suspend fun getProductById(id: Int): Product

    @Query("SELECT * FROM ${RoomConstants.PRODUCT} WHERE category_id = :categoryId")
    suspend fun getProductsByCategoryId(categoryId : Int): List<Product>

    @Query("SELECT * FROM ${RoomConstants.PRODUCT} WHERE product_name LIKE :value OR description LIKE :value")
    suspend fun getProductsBySearch(value: String): List<Product>

    @Query("SELECT * FROM ${RoomConstants.PRODUCT}")
    suspend fun getAllProducts(): List<Product>

    @Query("DELETE FROM ${RoomConstants.PRODUCT}")
    suspend fun deleteAllProducts()
}