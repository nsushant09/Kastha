package com.neupanesushant.kastha.appcore.koin_module

import com.neupanesushant.kastha.domain.usecase.AuthenticationUseCase
import com.neupanesushant.kastha.viewmodel.AuthenticationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModule = module {

    viewModel {
        AuthenticationViewModel(get<AuthenticationUseCase>())
    }

}