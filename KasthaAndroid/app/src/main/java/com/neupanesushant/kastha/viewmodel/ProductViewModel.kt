package com.neupanesushant.kastha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neupanesushant.kastha.core.ResponseResolver
import com.neupanesushant.kastha.data.local.ProductDao
import com.neupanesushant.kastha.data.repo.ProductRepo
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.extra.RecommendedDataManager
import com.neupanesushant.kastha.extra.Utils
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productRepo: ProductRepo,
    private val productDao: ProductDao
) : ViewModel() {

    private val _allProducts: MutableLiveData<List<Product>> = MutableLiveData()
    val allProduct: LiveData<List<Product>> get() = _allProducts

    private val _recommendedProducts: MutableLiveData<List<Product>> = MutableLiveData()
    val recommendedProducts: LiveData<List<Product>> get() = _recommendedProducts

    init {
        getAllProducts()
    }

    private fun getAllProducts() = viewModelScope.launch {

        val response = productRepo.all()
        ResponseResolver(response, onFailure = {
            _allProducts.value = productDao.getAllProducts()
        }) { products ->
            _allProducts.value = products
            products.forEach { productDao.insert(it) }
        }()
    }

    fun getRecommendedProducts() = viewModelScope.launch {
        val categoryIds = RecommendedDataManager.recommendedCategories
        val response = productRepo.getProductByRecommended(categoryIds)
        ResponseResolver(response, onFailure = {
            _recommendedProducts.value = productDao.getRecommendedProducts(categoryIds)
        }) { products ->
            _recommendedProducts.value = products
        }()
    }

    fun getSearchResults(searchValue: String) = _allProducts.value?.filter {
        Utils.isStringInTarget(it.name, searchValue)
                || Utils.isStringInTarget(it.category.name, searchValue)
    } ?: emptyList()
}