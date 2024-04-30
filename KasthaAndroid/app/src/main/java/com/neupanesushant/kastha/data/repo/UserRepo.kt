package com.neupanesushant.kastha.data.repo

import com.neupanesushant.kastha.core.Response
import com.neupanesushant.kastha.domain.model.User
import com.neupanesushant.kastha.domain.model.dto.UserUpdateDTO

interface UserRepo {
    suspend fun getUserDetail(userId: Int): Response<User>
    suspend fun getChatUsers(userIds: List<Int>): Response<List<User>>
    suspend fun updateUserDetail(user: UserUpdateDTO): Response<User>
}