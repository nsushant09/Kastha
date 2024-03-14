package com.neupanesushant.kastha.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.neupanesushant.kastha.domain.model.CartProduct
import com.neupanesushant.kastha.extra.RoomConstants

@Dao
interface CartProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(cartProduct: CartProduct)

    @Query("DELETE FROM ${RoomConstants.CART} WHERE id = :cartProductId")
    suspend fun remove(cartProductId: Int)

    @Query("SELECT * FROM ${RoomConstants.CART}")
    suspend fun getAllCartProducts(): List<CartProduct>
}