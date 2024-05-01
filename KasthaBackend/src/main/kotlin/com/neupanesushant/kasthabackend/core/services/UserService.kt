package com.neupanesushant.kasthabackend.core.services

import com.neupanesushant.kasthabackend.core.repo.UserRepo
import com.neupanesushant.kasthabackend.data.dtomodel.UserUpdateDTO
import com.neupanesushant.kasthabackend.data.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(
    @Autowired private val userRepo: UserRepo,
    private val passwordEncoder: PasswordEncoder,
) {
    fun getUser(userId: Int): User? {
        val user = userRepo.findById(userId).getOrNull() ?: return null
        return user;
    }

    fun updateUser(user: UserUpdateDTO): User {
        var password = user.password
        password = passwordEncoder.encode(password)
        if (password.isEmpty()) {
            password = userRepo.findById(user.id).getOrNull()?.password ?: ""
        }
        val toUpdateUser = User(
            user.id, user.firstName, user.lastName, user.email, password, user.gender, user.location, user.roles
        )

        val updatedUser = userRepo.save(toUpdateUser)
        return updatedUser
    }

    fun resetPassword(email: String, password: String): User? {
        val user = userRepo.findByEmail(email) ?: return null
        val updatedUser = userRepo.save(user.copy(password = passwordEncoder.encode(password)))
        return updatedUser
    }
}