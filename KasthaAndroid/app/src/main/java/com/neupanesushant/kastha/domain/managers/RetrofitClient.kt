package com.neupanesushant.kastha.domain.managers

import com.neupanesushant.kastha.BuildConfig
import com.neupanesushant.kastha.extra.Preferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object NetworkUtils {
    private val interceptor = HttpLoggingInterceptor()

    val loggingClient: OkHttpClient = OkHttpClient.Builder().apply {
        val level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        interceptor.level = level
        addInterceptor(interceptor)
    }.build()

    val authorizationClient: OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Authorization", Preferences.getAuthenticationToken() ?: "")
                .method(original.method(), original.body())
            chain.proceed(requestBuilder.build())
        }
    }.build()
}