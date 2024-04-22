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
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepo: CartRepo,
    private val cartProductDao: CartProductDao
) : ViewModel() {

    private val _allProducts: MutableLiveData<List<CartProduct>> = MutableLiveData()
    val allProducts: LiveData<List<CartProduct>> get() = _allProducts

    init {
        getAllCartProducts()
    }

    fun getAllCartProducts() = viewModelScope.launch {
        if (AppContext.isOffline) {
            _allProducts.value = cartProductDao.getAllCartProducts()
            return@launch
        }

        _allProducts.value = cartRepo.all(Preferences.getUserId())
    }

    fun addProductToCart(productId: Int) = viewModelScope.launch {
        _allProducts.value = cartRepo.add(productId, Preferences.getUserId()).also { cartProducts ->
            cartProducts.find { it.product.id == productId }?.let {
                cartProductDao.add(it)
            }
        }
    }

    fun removeProducts(cartProductIds: Collection<Int>) = viewModelScope.launch {
        _allProducts.value = cartRepo.remove(cartProductIds.toList())
        cartProductIds.forEach { cartProductId ->
            cartProductDao.remove(cartProductId)
        }
    }

    fun increment(cartProductId: Int) = viewModelScope.launch {
        val cartProduct = cartRepo.increment(cartProductId)
        _allProducts.value = _allProducts.value?.map { oldProduct ->
            if (oldProduct.id == cartProduct.id) cartProduct else oldProduct
        }
        cartProductDao.add(cartProduct)
    }

    fun decrement(cartProductId: Int) = viewModelScope.launch {
        val cartProduct = cartRepo.decrement(cartProductId)
        _allProducts.value = _allProducts.value?.map { oldProduct ->
            if (oldProduct.id == cartProduct.id) cartProduct else oldProduct
        }
        cartProductDao.add(cartProduct)
    }
}