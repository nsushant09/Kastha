package com.neupanesushant.kastha.appcore.koin_module

import android.content.Context
import androidx.room.Room
import com.neupanesushant.kastha.BuildConfig
import com.neupanesushant.kastha.data.local.CategoryDao
import com.neupanesushant.kastha.data.local.KasthaDatabase
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
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(NetworkUtils.loggingClient)
            .build()
            .create(AuthenticationEndpoint::class.java)
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
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

    single<KasthaDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            KasthaDatabase::class.java,
            BuildConfig.APPLICATION_ID + ".database"
        )
            .build()
    }

    single<CategoryDao> {
        get<KasthaDatabase>().categoryDao()
    }

    single {
        get<KasthaDatabase>().cartProductDao()
    }

    single {
        get<KasthaDatabase>().favouriteDao()
    }

    single {
        get<KasthaDatabase>().productDao()
    }

    single {
        get<KasthaDatabase>().userDao()
    }
}