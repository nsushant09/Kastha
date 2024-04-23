package com.neupanesushant.kastha.appcore.koin_module

import android.content.Context
import android.content.SharedPreferences
import com.neupanesushant.kastha.BuildConfig
import com.neupanesushant.kastha.data.repo.AlignmentRepo
import com.neupanesushant.kastha.data.repo.CartRepo
import com.neupanesushant.kastha.data.repo.CategoryRepo
import com.neupanesushant.kastha.data.repo.FavoriteRepo
import com.neupanesushant.kastha.data.repo.ProductRepo
import com.neupanesushant.kastha.data.repo.ReviewRepo
import com.neupanesushant.kastha.data.repo.UserRepo
import com.neupanesushant.kastha.domain.repo_impl.remote.AlignmentRemoteImpl
import com.neupanesushant.kastha.domain.repo_impl.remote.CartRemoteImpl
import com.neupanesushant.kastha.domain.repo_impl.remote.CategoryRemoteImpl
import com.neupanesushant.kastha.domain.repo_impl.remote.FavoriteRemoteImpl
import com.neupanesushant.kastha.domain.repo_impl.remote.ProductRemoteImpl
import com.neupanesushant.kastha.domain.repo_impl.remote.ReviewRemoteImpl
import com.neupanesushant.kastha.domain.repo_impl.remote.UserRemoteImpl
import com.neupanesushant.kastha.domain.usecase.AuthenticationUseCase
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


    single<AlignmentRepo> {
        AlignmentRemoteImpl(get())
    }

    single<FavoriteRepo> {
        FavoriteRemoteImpl(get())
    }

    single<CartRepo> {
        CartRemoteImpl(get())
    }

    single<ProductRepo> {
        ProductRemoteImpl(get())
    }

    single<ReviewRepo> {
        ReviewRemoteImpl(get())
    }

    single<UserRepo> {
        UserRemoteImpl(get())
    }
}