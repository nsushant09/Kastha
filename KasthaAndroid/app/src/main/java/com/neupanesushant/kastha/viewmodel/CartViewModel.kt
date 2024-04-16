package com.neupanesushant.kastha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neupanesushant.kastha.data.local.CartProductDao
import com.neupanesushant.kastha.data.repo.CartRepo
import com.neupanesushant.kastha.domain.model.CartProduct
import com.neupanesushant.kastha.extra.AppContext
import com.neupanesushant.kastha.extra.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepo: CartRepo,
    private val cartProductDao: CartProductDao
) : ViewModel() {

    private val _allProducts: MutableLiveData<List<CartProduct>> = MutableLiveData()
    val allProducts: LiveData<List<CartProduct>> get() = _allProducts
    private fun getAllCartProducts() = viewModelScope.launch {
        if (AppContext.isOffline) {
            _allProducts.value = cartProductDao.getAllCartProducts()
            return@launch
        }

        cartRepo.all(Preferences.getUserId())
            .flowOn(Dispatchers.IO)
            .catch { e -> }
            .collect {
                _allProducts.value = it
            }
    }

    fun addProductToCart(productId: Int) = viewModelScope.launch {
        val cartProduct = cartRepo.add(productId, Preferences.getUserId())
        cartProductDao.add(cartProduct)
    }

    fun removeProduct(cartProductId: Int) = viewModelScope.launch {
        cartRepo.remove(cartProductId)
        cartProductDao.remove(cartProductId)
    }

    fun increment(cartProductId: Int) = viewModelScope.launch {
        val cartProduct = cartRepo.increment(cartProductId)
        cartProductDao.add(cartProduct)
    }

    fun decrement(cartProductId: Int) = viewModelScope.launch {
        val cartProduct = cartRepo.decrement(cartProductId)
        cartProductDao.add(cartProduct)
    }

    fun removeProducts(cartProductIds: Collection<Int>) {
        cartProductIds.forEach { removeProduct(it) }
    }
}