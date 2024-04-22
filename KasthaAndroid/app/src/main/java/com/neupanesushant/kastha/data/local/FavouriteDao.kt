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

    @Query("DELETE FROM ${RoomConstants.FAVORITE} WHERE favourite_id = :productId")
    suspend fun removeFavorite(productId: Int)

    @Query("SELECT * FROM ${RoomConstants.FAVORITE} WHERE favourite_id = 1")
    suspend fun getFavourite(): Favourite

    @Query("DELETE FROM ${RoomConstants.FAVORITE} WHERE favourite_id = 1 AND products IN (:productIds)")
    suspend fun removeFavoritesByProductIds(productIds: List<Int>)
}