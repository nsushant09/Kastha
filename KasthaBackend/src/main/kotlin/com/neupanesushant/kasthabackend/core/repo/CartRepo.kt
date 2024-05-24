package com.neupanesushant.kasthabackend.core.repo

import com.neupanesushant.kasthabackend.data.model.Cart
import com.neupanesushant.kasthabackend.data.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface CartRepo : JpaRepository<Cart, Int> {
    fun findByUser(user: User) : Cart?
}