package com.neupanesushant.kastha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neupanesushant.kastha.core.ResponseResolver
import com.neupanesushant.kastha.data.repo.ReviewRepo
import com.neupanesushant.kastha.domain.model.ReviewResponse
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val reviewRepo: ReviewRepo
) : ViewModel() {

    val productReviews: LiveData<List<ReviewResponse>> get() = _productReviews
    private val _productReviews: MutableLiveData<List<ReviewResponse>> = MutableLiveData(emptyList())

    fun getProductReview(productId: Int) = viewModelScope.launch {
        val response = reviewRepo.getReviewOf(productId)
        ResponseResolver(response, onFailure = {
            _productReviews.value = emptyList()
        }, onSuccess = {
            _productReviews.value = it
        })
    }
}