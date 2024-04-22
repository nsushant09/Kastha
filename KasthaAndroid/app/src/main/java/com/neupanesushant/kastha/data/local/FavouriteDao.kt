package com.neupanesushant.kastha.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.neupanesushant.kastha.domain.model.FavouriteProduct
import com.neupanesushant.kastha.extra.RoomConstants

@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(favorite: FavouriteProduct)

    @Query("DELETE FROM ${RoomConstants.FAVORITE} WHERE favourite_product_id = :productId")
    suspend fun removeFavorite(productId: Int)

    @Query("SELECT * FROM ${RoomConstants.FAVORITE}")
    suspend fun getFavourites(): List<FavouriteProduct>

    @Query("DELETE FROM ${RoomConstants.FAVORITE} WHERE product_id IN (:productIds)")
    suspend fun removeFavoritesByProductIds(productIds: List<Int>)
}