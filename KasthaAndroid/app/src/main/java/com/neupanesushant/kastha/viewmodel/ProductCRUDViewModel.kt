package com.neupanesushant.kastha.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neupanesushant.kastha.core.ResponseResolver
import com.neupanesushant.kastha.data.repo.ImageRepo
import com.neupanesushant.kastha.data.repo.ModelRepo
import com.neupanesushant.kastha.data.repo.ProductRepo
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class ProductCRUDViewModel(
    private val imageRepo: ImageRepo,
    private val modelRepo: ModelRepo,
    private val productRepo: ProductRepo
) : ViewModel() {

    private val _productImages = MutableLiveData<List<Bitmap>>()
    val productImages get() : LiveData<List<Bitmap>> = _productImages

    private val _productModel = MutableLiveData<Uri>()
    val productModel get() : LiveData<Uri> = _productModel

    fun addImages() = viewModelScope.launch {
        if (productImages.value == null) return@launch
        val response = imageRepo.uploadImages(productImages.value!!)
        ResponseResolver(response, onFailure = {
            Log.d("IMAGE_TAG", "Could not upload Image")
        }, onSuccess = {
            Log.d("IMAGE_TAG", it.data.toString())
        })()
    }

    fun setProductImages(images: List<Bitmap>) {
        _productImages.value = images
    }
}