package com.neupanesushant.kastha.domain.managers

import com.neupanesushant.kastha.BuildConfig
import com.neupanesushant.kastha.extra.Preferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import kotlin.math.log

object NetworkUtils {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        val level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        this.level = level
    }

    val loggingClient: OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(loggingInterceptor)
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
    }.build()
}