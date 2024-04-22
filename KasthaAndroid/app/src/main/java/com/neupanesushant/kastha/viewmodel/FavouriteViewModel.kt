package com.neupanesushant.kastha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neupanesushant.kastha.core.ResponseResolver
import com.neupanesushant.kastha.data.local.FavouriteDao
import com.neupanesushant.kastha.data.repo.FavoriteRepo
import com.neupanesushant.kastha.domain.model.FavouriteProduct
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
            _favouriteProducts.value = favouriteDao.getFavourites().map { it.product }
        }, onSuccess = {
            _favouriteProducts.value = it
        })()
    }

    fun addToFavourite(product: Product, onFailure: (String) -> Unit) = viewModelScope.launch {
        val response = favoriteRepo.add(product.id, Preferences.getUserId())
        ResponseResolver(response, onFailure = onFailure, onSuccess = {
            _favouriteProducts.value = it
            favouriteDao.addFavorite(FavouriteProduct(product = product))
        })()
    }

    fun removeFromFavourite(productIds: Collection<Int>, onFailure: (String) -> Unit) =
        viewModelScope.launch {
            val response = favoriteRepo.remove(productIds.toList(), Preferences.getUserId())
            ResponseResolver(response, onFailure = onFailure, onSuccess = {
                _favouriteProducts.value = it
                productIds.forEach {
                    favouriteDao.removeFavorite(it)
                }
            })()
        }

    fun removeFromFavourite(productId: Int, onFailure: (String) -> Unit) = removeFromFavourite(
        listOf(productId), onFailure
    )
}