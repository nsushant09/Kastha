package com.neupanesushant.kastha.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neupanesushant.kastha.core.ResponseResolver
import com.neupanesushant.kastha.data.repo.ReviewRepo
import com.neupanesushant.kastha.domain.model.Review
import com.neupanesushant.kastha.domain.model.ReviewResponse
import com.neupanesushant.kastha.extra.Preferences
import kotlinx.coroutines.launch
import java.sql.Date

class ReviewViewModel(
    private val reviewRepo: ReviewRepo
) : ViewModel() {

    val productReviews: LiveData<List<ReviewResponse>> get() = _productReviews
    private val _productReviews: MutableLiveData<List<ReviewResponse>> =
        MutableLiveData(emptyList())

    fun getProductReview(productId: Int) = viewModelScope.launch {
        val response = reviewRepo.getReviewOf(productId)
        ResponseResolver(response, onFailure = {
            _productReviews.value = emptyList()
        }, onSuccess = {
            _productReviews.value = it
        })
    }

    @SuppressLint("SimpleDateFormat")
    fun addReview(productId: Int, rating: Int, description: String, onSuccess: () -> Unit) =
        viewModelScope.launch {
            val review = Review(0, description, rating, Date(System.currentTimeMillis()).toString())
            val response = reviewRepo.add(productId, Preferences.getUserId(), review)
            ResponseResolver(response, onFailure = {
                Log.d("TAG", it)
            }, onSuccess = {
                val temp = _productReviews.value?.toMutableList() ?: mutableListOf()
                temp.add(it)
                _productReviews.value = temp
                onSuccess()
            })()
        }
}