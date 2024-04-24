package com.neupanesushant.kastha.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neupanesushant.kastha.data.repo.ImageRepo
import com.neupanesushant.kastha.data.repo.ModelRepo
import com.neupanesushant.kastha.data.repo.ProductRepo

class ProductCRUDViewModel(
    private val imageRepo: ImageRepo,
    private val modelRepo: ModelRepo,
    private val productRepo: ProductRepo
) : ViewModel() {

    private val _productImages = MutableLiveData<List<Uri>>()
    val productImages get() : LiveData<List<Uri>> = _productImages

    private val _productModel = MutableLiveData<Uri>()
    val productModel get() : LiveData<Uri> = _productModel
}