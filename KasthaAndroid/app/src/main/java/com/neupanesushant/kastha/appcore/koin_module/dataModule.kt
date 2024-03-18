package com.neupanesushant.kastha.appcore.koin_module

import android.content.Context
import com.neupanesushant.kastha.BuildConfig
import com.neupanesushant.kastha.data.remote.AuthenticationEndpoint
import com.neupanesushant.kastha.data.remote.Endpoint
import com.neupanesushant.kastha.domain.managers.NetworkUtils
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(NetworkUtils.loggingClient)
            .build()
            .create(AuthenticationEndpoint::class.java)
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(NetworkUtils.loggingClient)
            .client(NetworkUtils.authorizationClient)
            .build()
            .create(Endpoint::class.java)
    }

    single {
        androidApplication().getSharedPreferences(
            BuildConfig.APPLICATION_ID + ".preferences",
            Context.MODE_PRIVATE
        )
    }
}