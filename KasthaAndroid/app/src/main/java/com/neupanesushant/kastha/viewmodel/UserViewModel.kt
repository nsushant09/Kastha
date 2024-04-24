package com.neupanesushant.kastha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neupanesushant.kastha.core.ResponseResolver
import com.neupanesushant.kastha.data.repo.UserRepo
import com.neupanesushant.kastha.domain.model.User
import com.neupanesushant.kastha.extra.Preferences
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserViewModel(
    private val userRepo: UserRepo
) : ViewModel(){

    private val _userDetail: MutableLiveData<User> = MutableLiveData()
    val userDetail: LiveData<User> get() = _userDetail

    init {
        getUserDetail()
    }

    fun getUserDetail() = viewModelScope.launch {
        val response = userRepo.getUserDetail(Preferences.getUserId())
        ResponseResolver(response, onFailure = {

        }, onSuccess = {
            _userDetail.value = it
        })()
    }
}