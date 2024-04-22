package com.neupanesushant.kastha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neupanesushant.kastha.core.ResponseResolver
import com.neupanesushant.kastha.data.local.CartProductDao
import com.neupanesushant.kastha.data.repo.CartRepo
import com.neupanesushant.kastha.domain.model.CartProduct
import com.neupanesushant.kastha.extra.Preferences
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepo: CartRepo, private val cartProductDao: CartProductDao
) : ViewModel() {

    private val _allProducts: MutableLiveData<List<CartProduct>> = MutableLiveData()
    val allProducts: LiveData<List<CartProduct>> get() = _allProducts

    init {
        getAllCartProducts()
    }

    fun getAllCartProducts() = viewModelScope.launch {
        val response = cartRepo.all(Preferences.getUserId())
        ResponseResolver(response, onFailure = {
            _allProducts.value = cartProductDao.getAllCartProducts()
        }, onSuccess = {
            _allProducts.value = it
        })()
    }

    fun addProductToCart(productId: Int, onFailure: (String) -> Unit) = viewModelScope.launch {
        val response = cartRepo.add(productId, Preferences.getUserId())
        ResponseResolver(response, onFailure = onFailure, onSuccess = {
            _allProducts.value = it
            it.find { it.product.id == productId }?.let { cartProductDao.add(it) }
        })()
    }

    fun removeProducts(cartProductIds: Collection<Int>, onFailure: (String) -> Unit) =
        viewModelScope.launch {
            val response = cartRepo.remove(cartProductIds.toList())
            ResponseResolver(response, onFailure = onFailure, onSuccess = {
                _allProducts.value = it
                cartProductIds.forEach { id ->
                    cartProductDao.remove(id)
                }
            })()
        }

    fun increment(cartProductId: Int, onFailure: (String) -> Unit) = viewModelScope.launch {
        val response = cartRepo.increment(cartProductId)
        ResponseResolver(response, onFailure = onFailure, onSuccess = {
            _allProducts.value = _allProducts.value?.map { oldProduct ->
                if (oldProduct.id == it.id) it else oldProduct
            }
            cartProductDao.add(it)
        })
    }

    fun decrement(cartProductId: Int, onFailure: (String) -> Unit) = viewModelScope.launch {
        val response = cartRepo.decrement(cartProductId)
        ResponseResolver(response, onFailure = onFailure, onSuccess = {
            _allProducts.value = _allProducts.value?.map { oldProduct ->
                if (oldProduct.id == it.id) it else oldProduct
            }
            cartProductDao.add(it)
        })
    }

    fun removeProduct(productId: Int, onFailure: (String) -> Unit) =
        removeProducts(listOf(productId), onFailure)
}