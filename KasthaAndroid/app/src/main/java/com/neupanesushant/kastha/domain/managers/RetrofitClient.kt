package com.neupanesushant.kastha.domain.managers

import com.neupanesushant.kastha.BuildConfig
import com.neupanesushant.kastha.extra.Preferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object NetworkUtils {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        val level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        this.level = level
    }

    val loggingClient: OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(loggingInterceptor)
        connectTimeout(2, TimeUnit.SECONDS)
        readTimeout(15, TimeUnit.SECONDS)
        writeTimeout(15, TimeUnit.SECONDS)
    }.build()

    val authorizationClient: OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(loggingInterceptor)
        addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Authorization", Preferences.getAuthenticationToken() ?: "")
                .method(original.method(), original.body())
            chain.proceed(requestBuilder.build())
        }
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
    }.build()
}