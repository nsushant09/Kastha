package com.neupanesushant.kastha.domain.usecase

import com.neupanesushant.kastha.core.requestHandler
import com.neupanesushant.kastha.data.remote.AuthenticationEndpoint
import com.neupanesushant.kastha.domain.model.LoginDTO
import com.neupanesushant.kastha.domain.model.dto.AuthResponse
import com.neupanesushant.kastha.domain.model.dto.RegisterDTO

class AuthenticationUseCase(
    private val authenticationEndpoint: AuthenticationEndpoint
) {
    suspend fun login(email: String, password: String) =
        requestHandler { authenticationEndpoint.login(LoginDTO(email, password)) }

    suspend fun register(registerDTO: RegisterDTO) =
        requestHandler {
            authenticationEndpoint.register(
                registerDTO
            )
        }


    suspend fun sendOTP(email: String) =
        requestHandler {
            authenticationEndpoint.sendOTP(email)
        }

    suspend fun validateLogin(email: String, password: String) =
        requestHandler {
            authenticationEndpoint.validateLogin(LoginDTO(email, password))
        }
}