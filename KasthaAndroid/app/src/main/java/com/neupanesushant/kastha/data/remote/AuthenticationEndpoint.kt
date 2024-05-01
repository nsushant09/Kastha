package com.neupanesushant.kastha.data.remote

import com.neupanesushant.kastha.domain.model.KeyValue
import com.neupanesushant.kastha.domain.model.LoginDTO
import com.neupanesushant.kastha.domain.model.OTPMailResponse
import com.neupanesushant.kastha.domain.model.dto.AuthResponse
import com.neupanesushant.kastha.domain.model.dto.BaseResponse
import com.neupanesushant.kastha.domain.model.dto.RegisterDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthenticationEndpoint {
    @POST("auth/login")
    suspend fun login(
        @Body loginDTO: LoginDTO
    ): Response<AuthResponse>

    @POST("auth/register")
    suspend fun register(
        @Body registerDTO: RegisterDTO
    ): Response<KeyValue<String, String>>

    @FormUrlEncoded
    @POST("mail/otp")
    suspend fun sendOTP(
        @Field("email") email: String
    ): Response<OTPMailResponse>

    @POST("auth/loginValidation")
    suspend fun validateLogin(
        @Body loginDTO: LoginDTO
    ): Response<BaseResponse<String>>

    @FormUrlEncoded
    @POST("mail/password_reset_otp")
    suspend fun sendPasswordResetOTP(
        @Field("email") email: String
    ): Response<OTPMailResponse>

    @FormUrlEncoded
    @PATCH("user/resetPassword")
    suspend fun resetPassword(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<BaseResponse<String>>
}