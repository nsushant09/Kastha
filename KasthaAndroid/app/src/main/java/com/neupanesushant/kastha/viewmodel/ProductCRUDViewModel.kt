package com.neupanesushant.kastha.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neupanesushant.kastha.core.Response
import com.neupanesushant.kastha.core.ResponseResolver
import com.neupanesushant.kastha.core.State
import com.neupanesushant.kastha.data.repo.ImageRepo
import com.neupanesushant.kastha.data.repo.ModelRepo
import com.neupanesushant.kastha.data.repo.ProductRepo
import com.neupanesushant.kastha.domain.model.Category
import com.neupanesushant.kastha.domain.model.Image
import com.neupanesushant.kastha.domain.model.ObjectModel
import com.neupanesushant.kastha.domain.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class ProductCRUDViewModel(
    private val imageRepo: ImageRepo,
    private val modelRepo: ModelRepo,
    private val productRepo: ProductRepo
) : ViewModel() {

    private val _addProductState = MutableLiveData<State<Product>>(State.Default)
    val addProductState: LiveData<State<Product>> get() = _addProductState

    private val _productImages = MutableLiveData<List<Bitmap>>()
    val productImages get() : LiveData<List<Bitmap>> = _productImages

    private val _productModel = MutableLiveData<MultipartBody.Part>()
    val productModel get() : LiveData<MultipartBody.Part> = _productModel

    suspend fun addImages(): List<String> {
        return withContext(Dispatchers.IO) {
            if (productImages.value == null) {
                return@withContext emptyList<String>()
            }
            val response = imageRepo.uploadImages(productImages.value!!)
            if (response is Response.Success) {
                response.responseData.data
            } else {
                emptyList()
            }
        }
    }

    fun setProductImages(images: List<Bitmap>) {
        _productImages.value = images
    }

    suspend fun addModel(): String? {
        return withContext(Dispatchers.IO) {
            if (productModel.value == null) {
                return@withContext null
            }
            val response = modelRepo.uploadModel(productModel.value!!)
            if (response is Response.Success) {
                response.responseData.data
            } else {
                null
            }
        }
    }


    fun setProductModel(file: MultipartBody.Part) {
        _productModel.value = file
    }

    fun addProduct(
        name: String, description: String, price: Float, stockQuantity: Int, category: Category
    ) = viewModelScope.launch {

        _addProductState.value = State.Loading

        val deferredImage = async { addImages() }
        val deferredModel = async { addModel() }
        val product = Product(
            id = 0,
            name = name,
            description = description,
            price = price,
            stockQuantity = stockQuantity,
            category = category,
            images = deferredImage.await().map { Image(0, it) },
            model = deferredModel.await()?.let { ObjectModel(0, it) }
        )

        val response = productRepo.add(product)
        ResponseResolver(response, onFailure = {
            _addProductState.value = State.Error("Could not add product. Please try again")
        }, onSuccess = {
            _addProductState.value = State.Success(it)
        })()
    }
}
