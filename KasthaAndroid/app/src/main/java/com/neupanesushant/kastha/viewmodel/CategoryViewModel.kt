package com.neupanesushant.kastha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neupanesushant.kastha.core.ResponseResolver
import com.neupanesushant.kastha.data.local.CategoryDao
import com.neupanesushant.kastha.data.repo.CategoryRepo
import com.neupanesushant.kastha.domain.model.Category
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryRepo: CategoryRepo,
    private val categoryDao: CategoryDao
) : ViewModel() {

    private val _categories: MutableLiveData<List<Category>> = MutableLiveData()
    val categories: LiveData<List<Category>> get() = _categories

    init {
        getCategories()
    }

    private fun getCategories() = viewModelScope.launch {
        val response = categoryRepo.getCategories()
        ResponseResolver(response, onFailure = {
            _categories.value = categoryDao.getCategories()
        }, onSuccess = {
            _categories.value = it
            it.forEach { categoryDao.insert(it) }
        })()
    }
}

