package com.neupanesushant.kastha.data.remote

import com.neupanesushant.kastha.domain.model.LoginDTO
import com.neupanesushant.kastha.domain.model.OTPMailResponse
import com.neupanesushant.kastha.domain.model.dto.AuthResponse
import com.neupanesushant.kastha.domain.model.dto.RegisterDTO
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthenticationEndpoint {
    @POST("auth/login")
    suspend fun login(
        @Body loginDTO: LoginDTO
    ): AuthResponse

    @POST("auth/register")
    suspend fun register(
        @Body registerDTO: RegisterDTO
    ): AuthResponse

    @FormUrlEncoded
    @POST("mail/otp")
    suspend fun sendOTP(
        @Field("email") email: String
    ): OTPMailResponse
}