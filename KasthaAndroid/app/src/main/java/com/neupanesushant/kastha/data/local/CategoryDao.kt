package com.neupanesushant.kastha.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.neupanesushant.kastha.domain.model.Category
import com.neupanesushant.kastha.extra.RoomConstants

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(category: Category)

    @Delete
    suspend fun remove(category: Category)

    @Query("SELECT * FROM ${RoomConstants.CATEGORY}")
    suspend fun getCategories(): List<Category>

    @Query("SELECT * FROM ${RoomConstants.CATEGORY} WHERE id = :id")
    suspend fun getCategoryById(id: Int): Category

    @Query("DELETE FROM ${RoomConstants.CATEGORY}")
    suspend fun deleteAllCategories()
}