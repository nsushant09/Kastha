package com.neupanesushant.kasthabackend.core.repo

import com.neupanesushant.kasthabackend.data.model.User
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.Optional

interface UserRepo : CoroutineCrudRepository<User, Int> {
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
}