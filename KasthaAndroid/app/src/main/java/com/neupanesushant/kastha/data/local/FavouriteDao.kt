package com.neupanesushant.kastha.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.neupanesushant.kastha.domain.model.Favourite
import com.neupanesushant.kastha.extra.RoomConstants

@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(favorite: Favourite)

    @Query("DELETE FROM ${RoomConstants.FAVORITE} WHERE id = :productId")
    suspend fun removeFavorite(productId : Int)

    @Query("SELECT * FROM ${RoomConstants.FAVORITE} WHERE userId = :userId")
    suspend fun getAllFavorites(userId : Int): Favourite

    @Query("DELETE FROM ${RoomConstants.FAVORITE} WHERE id IN (:productIds)")
    suspend fun removeFavoritesByProductIds(productIds: List<Int>)
}