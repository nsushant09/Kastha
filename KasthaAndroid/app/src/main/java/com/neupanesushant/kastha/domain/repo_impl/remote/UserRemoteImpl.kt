package com.neupanesushant.kastha.domain.repo_impl.remote

import com.neupanesushant.kastha.core.Response
import com.neupanesushant.kastha.core.requestHandler
import com.neupanesushant.kastha.data.remote.Endpoint
import com.neupanesushant.kastha.data.repo.UserRepo
import com.neupanesushant.kastha.domain.model.User
import com.neupanesushant.kastha.domain.model.dto.UserUpdateDTO

class UserRemoteImpl(
    private val endpoint: Endpoint
) : UserRepo {
    override suspend fun getUserDetail(userId: Int): Response<User> = requestHandler {
        endpoint.getUserDetail(userId)
    }

    override suspend fun getChatUsers(userIds: List<Int>): Response<List<User>> = requestHandler {
        endpoint.getChatUsers(userIds)
    }

    override suspend fun updateUserDetail(user: UserUpdateDTO): Response<User> = requestHandler {
        endpoint.updateUserDetail(user)
    }


}