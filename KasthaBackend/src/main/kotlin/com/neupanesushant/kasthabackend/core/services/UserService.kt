package com.neupanesushant.kasthabackend.core.services

import com.neupanesushant.kasthabackend.core.repo.UserRepo
import com.neupanesushant.kasthabackend.data.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(
    @Autowired private val userRepo: UserRepo
) {
    fun getUser(userId: Int): User? {
        val user = userRepo.findById(userId).getOrNull() ?: return null
        return user;
    }
}