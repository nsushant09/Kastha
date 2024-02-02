package com.neupanesushant.kasthabackend.core.repo

import com.neupanesushant.kasthabackend.data.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.Optional

interface UserRepo : JpaRepository<User, Int> {
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
}