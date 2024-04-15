package com.neupanesushant.kastha.appcore.koin_module

import com.neupanesushant.kastha.data.local.CategoryDao
import com.neupanesushant.kastha.data.repo.CategoryRepo
import com.neupanesushant.kastha.domain.usecase.AuthenticationUseCase
import com.neupanesushant.kastha.viewmodel.AuthenticationViewModel
import com.neupanesushant.kastha.viewmodel.CategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModule = module {

    viewModel {
        AuthenticationViewModel(get<AuthenticationUseCase>())
    }

    viewModel {
        CategoryViewModel(get<CategoryRepo>(), get<CategoryDao>())
    }
}