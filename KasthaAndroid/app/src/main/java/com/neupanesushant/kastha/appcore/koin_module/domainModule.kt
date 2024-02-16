package com.neupanesushant.kastha.appcore.koin_module

import android.content.Context
import android.content.SharedPreferences
import com.neupanesushant.kastha.BuildConfig
import com.neupanesushant.kastha.domain.usecase.AuthenticationUseCase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val domainModule = module {
    single {
        androidApplication().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    }

    single{
        AuthenticationUseCase(get())
    }
}