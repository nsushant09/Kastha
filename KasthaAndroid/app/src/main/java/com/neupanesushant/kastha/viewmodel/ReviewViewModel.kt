package com.neupanesushant.kastha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neupanesushant.kastha.data.repo.ReviewRepo
import com.neupanesushant.kastha.domain.model.ReviewResponse
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val reviewRepo: ReviewRepo
) : ViewModel() {

    private val _productReviews: MutableLiveData<List<ReviewResponse>> = MutableLiveData(emptyList())
    val productReviews: LiveData<List<ReviewResponse>> get() = _productReviews

    fun getProductReview(productId: Int) = viewModelScope.launch {
        _productReviews.value = reviewRepo.getReviewOf(productId)
    }
}