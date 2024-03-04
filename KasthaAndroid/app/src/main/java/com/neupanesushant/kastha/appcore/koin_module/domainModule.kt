package com.neupanesushant.kastha.appcore.koin_module

import android.content.Context
import android.content.SharedPreferences
import com.neupanesushant.kastha.BuildConfig
import com.neupanesushant.kastha.data.repo.AlignmentRepo
import com.neupanesushant.kastha.data.repo.CartRepo
import com.neupanesushant.kastha.data.repo.CategoryRepo
import com.neupanesushant.kastha.data.repo.FavoriteRepo
import com.neupanesushant.kastha.domain.repo_impl.remote.AlignmentRemoteImpl
import com.neupanesushant.kastha.domain.repo_impl.remote.CartRemoteImpl
import com.neupanesushant.kastha.domain.repo_impl.remote.CategoryRemoteImpl
import com.neupanesushant.kastha.domain.repo_impl.remote.FavoriteRemoteImpl
import com.neupanesushant.kastha.domain.usecase.AlignmentUseCase
import com.neupanesushant.kastha.domain.usecase.AuthenticationUseCase
import com.neupanesushant.kastha.domain.usecase.CartUseCase
import com.neupanesushant.kastha.domain.usecase.CategoryUseCase
import com.neupanesushant.kastha.domain.usecase.FavoriteUseCase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val domainModule = module {
    single<SharedPreferences> {
        androidApplication().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    }

    single {
        AuthenticationUseCase(get())
    }


    single<CategoryRepo> {
        CategoryRemoteImpl(get())
    }
    single {
        CategoryUseCase(get())
    }


    single<AlignmentRepo> {
        AlignmentRemoteImpl(get())
    }

    single {
        AlignmentUseCase(get())
    }

    single<FavoriteRepo> {
        FavoriteRemoteImpl(get())
    }
    single {
        FavoriteUseCase(get())
    }

    single<CartRepo> {
        CartRemoteImpl(get())
    }
    single {
        CartUseCase(get())
    }
}