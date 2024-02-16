package com.neupanesushant.kastha.domain.usecase

import com.neupanesushant.kastha.core.safeApiCall
import com.neupanesushant.kastha.data.remote.AuthenticationEndpoint
import com.neupanesushant.kastha.domain.model.KeyValue
import com.neupanesushant.kastha.domain.model.dto.RegisterDTO

class AuthenticationUseCase(
    private val authenticationEndpoint: AuthenticationEndpoint
) {
    suspend fun login(email: String, password: String) =
        safeApiCall { authenticationEndpoint.login(KeyValue(email, password)) }

    suspend fun register(firstName: String, lastName: String, email: String, password: String) =
        safeApiCall {
            authenticationEndpoint.register(
                RegisterDTO(firstName, lastName, email, password)
            )
        }
}