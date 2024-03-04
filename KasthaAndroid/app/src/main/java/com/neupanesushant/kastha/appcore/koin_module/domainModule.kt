package com.neupanesushant.kastha.appcore.koin_module

import android.content.Context
import com.neupanesushant.kastha.BuildConfig
import com.neupanesushant.kastha.data.repo.AlignmentRepo
import com.neupanesushant.kastha.data.repo.CategoryRepo
import com.neupanesushant.kastha.domain.repo_impl.remote.AlignmentRemoteImpl
import com.neupanesushant.kastha.domain.repo_impl.remote.CategoryRemoteImpl
import com.neupanesushant.kastha.domain.usecase.AlignmentUseCase
import com.neupanesushant.kastha.domain.usecase.AuthenticationUseCase
import com.neupanesushant.kastha.domain.usecase.CategoryUseCase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val domainModule = module {
    single {
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

}