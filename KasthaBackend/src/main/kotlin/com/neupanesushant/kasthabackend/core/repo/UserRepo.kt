package com.neupanesushant.kasthabackend.core.repo

import com.neupanesushant.kasthabackend.data.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepo : JpaRepository<User, Int> {
    fun findByEmail(email : String) : Optional<User>
    fun existsByEmail(email : String) : Boolean
}