package com.neupanesushant.kasthabackend.core.controller

import com.neupanesushant.kasthabackend.core.services.UserService
import com.neupanesushant.kasthabackend.data.dtomodel.UserDTO
import com.neupanesushant.kasthabackend.data.dtomodel.UserUpdateDTO
import com.neupanesushant.kasthabackend.data.mapper.Mapper
import com.neupanesushant.kasthabackend.data.model.BaseResponse
import com.neupanesushant.kasthabackend.data.model.Product
import com.neupanesushant.kasthabackend.data.model.User
import org.apache.coyote.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(
    @Autowired private val userService: UserService
) {
    @GetMapping("/{user_id}")
    fun getUser(@PathVariable("user_id") userId: Int): ResponseEntity<UserDTO> {
        val user = userService.getUser(userId) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(Mapper.toDto(user))
    }

    @GetMapping("/latest-chat")
    fun getUsers(@RequestParam("user_ids") userIds: List<Int>): ResponseEntity<List<UserDTO>> {
        val users = mutableListOf<UserDTO>()
        for (userId in userIds) {
            userService.getUser(userId)?.let {
                users.add(Mapper.toDto(it))
            }
        }
        return ResponseEntity.ok(users)
    }

    @PatchMapping
    fun updateUser(@RequestBody user: UserUpdateDTO): ResponseEntity<UserDTO> {
        val updatedUser = userService.updateUser(user)
        return ResponseEntity.ok(Mapper.toDto(updatedUser))
    }

    @PatchMapping("/resetPassword")
    fun resetUserPassword(
        @RequestParam("email") email: String,
        @RequestParam("password") password: String
    ): ResponseEntity<BaseResponse<String>> {
        val updatedUser = userService.resetPassword(email, password) ?: return ResponseEntity.ok(
            BaseResponse(
                false,
                "User email could not be found"
            )
        )
        return ResponseEntity.ok(BaseResponse(success = true, "Your password has been reset"))
    }
}