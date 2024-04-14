package com.neupanesushant.kastha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neupanesushant.kastha.domain.model.Category
import com.neupanesushant.kastha.domain.usecase.CategoryUseCase
import com.neupanesushant.kastha.domain.usecase.ProductUseCase
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryUseCase: CategoryUseCase,
    private val productUseCase: ProductUseCase
) : ViewModel() {

    private val _categories: MutableLiveData<List<Category>> = MutableLiveData()
    val categories: LiveData<List<Category>> get() = _categories

    init {
        viewModelScope.launch {
            _categories.value = categoryUseCase.getCategories()
        }
    }
}