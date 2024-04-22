package com.neupanesushant.kastha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neupanesushant.kastha.core.ResponseResolver
import com.neupanesushant.kastha.data.local.FavouriteDao
import com.neupanesushant.kastha.data.repo.FavoriteRepo
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.extra.Preferences
import kotlinx.coroutines.launch

class FavouriteViewModel(
    private val favoriteRepo: FavoriteRepo,
    private val favouriteDao: FavouriteDao
) : ViewModel() {

    private val _favouriteProducts = MutableLiveData<List<Product>>()
    val favouriteProducts: LiveData<List<Product>> get() = _favouriteProducts

    init {
        getAllFavouriteProducts()
    }

    fun getAllFavouriteProducts() = viewModelScope.launch {
        val response = favoriteRepo.all(Preferences.getUserId())
        ResponseResolver(response, onFailure = {
            _favouriteProducts.value = favouriteDao.getFavourite().products.toList()
        }, onSuccess = {
            _favouriteProducts.value = it
        })()
    }

    fun addToFavourite(productId: Int) = viewModelScope.launch {
        _favouriteProducts.value = favoriteRepo.add(productId, Preferences.getUserId())
    }

    fun removeFromFavourite(productIds: Collection<Int>) = viewModelScope.launch {
        _favouriteProducts.value = favoriteRepo.remove(productIds.toList(), Preferences.getUserId())
    }
}