package com.neupanesushant.kastha.data.repo

import com.neupanesushant.kastha.core.Response
import com.neupanesushant.kastha.domain.model.User

interface UserRepo {
    suspend fun getUserDetail(userId: Int): Response<User>
}