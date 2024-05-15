package com.neupanesushant.kastha.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.neupanesushant.kastha.domain.model.User
import com.neupanesushant.kastha.extra.RoomConstants

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Delete
    suspend fun remove(user: User)

    @Query("SELECT * FROM ${RoomConstants.USER} WHERE user_id = :id LIMIT 1")
    suspend fun getUser(id: Int): User
}