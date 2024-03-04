package com.neupanesushant.kastha.domain.managers

import com.neupanesushant.kastha.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val interceptor = HttpLoggingInterceptor()

    private val client = OkHttpClient.Builder().apply {
        val level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        interceptor.level = level
        addInterceptor(interceptor)
    }.build()

    val instance: Retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .baseUrl(BuildConfig.BASE_URL).build()
}