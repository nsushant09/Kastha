package com.neupanesushant.kastha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neupanesushant.kastha.data.local.CategoryDao
import com.neupanesushant.kastha.data.repo.CategoryRepo
import com.neupanesushant.kastha.domain.model.Category
import com.neupanesushant.kastha.extra.AppContext
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

    private fun getCategories() {
        viewModelScope.launch {
            val databaseCategories = categoryDao.getCategories()

            if (AppContext.isOffline) {
                _categories.value = databaseCategories
                return@launch
            }

            _categories.value = categoryRepo.getCategories()
            if (_categories.value != null || _categories.value!! != databaseCategories) {
                refreshCachedCategories(_categories.value!!)
            }
        }
    }

    private suspend fun refreshCachedCategories(categories: List<Category>) {
        categoryDao.deleteAllCategories()
        categories.forEach {
            categoryDao.insert(it)
        }
    }
}