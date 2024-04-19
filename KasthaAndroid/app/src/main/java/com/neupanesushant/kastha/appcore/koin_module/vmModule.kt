package com.neupanesushant.kastha.appcore.koin_module

import com.neupanesushant.kastha.data.local.CartProductDao
import com.neupanesushant.kastha.data.local.CategoryDao
import com.neupanesushant.kastha.data.local.FavouriteDao
import com.neupanesushant.kastha.data.local.ProductDao
import com.neupanesushant.kastha.data.repo.CartRepo
import com.neupanesushant.kastha.data.repo.CategoryRepo
import com.neupanesushant.kastha.data.repo.FavoriteRepo
import com.neupanesushant.kastha.data.repo.ProductRepo
import com.neupanesushant.kastha.data.repo.ReviewRepo
import com.neupanesushant.kastha.domain.usecase.AuthenticationUseCase
import com.neupanesushant.kastha.viewmodel.AuthenticationViewModel
import com.neupanesushant.kastha.viewmodel.CartViewModel
import com.neupanesushant.kastha.viewmodel.CategoryViewModel
import com.neupanesushant.kastha.viewmodel.FavouriteViewModel
import com.neupanesushant.kastha.viewmodel.ProductViewModel
import com.neupanesushant.kastha.viewmodel.ReviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModule = module {

    viewModel {
        AuthenticationViewModel(get<AuthenticationUseCase>())
    }

    viewModel {
        CategoryViewModel(get<CategoryRepo>(), get<CategoryDao>())
    }

    viewModel {
        ProductViewModel(get<ProductRepo>(), get<ProductDao>())
    }

    viewModel {
        CartViewModel(get<CartRepo>(), get<CartProductDao>())
    }

    viewModel {
        FavouriteViewModel(get<FavoriteRepo>(), get<FavouriteDao>())
    }

    viewModel {
        ReviewViewModel(get<ReviewRepo>())
    }
}