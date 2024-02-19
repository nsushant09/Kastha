package com.neupanesushant.kasthabackend.core.repo

import com.neupanesushant.kasthabackend.data.model.Favorite
import com.neupanesushant.kasthabackend.data.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface FavoriteRepo : JpaRepository<Favorite, Int> {
    fun findByUser(user: User) : Favorite?
}