package com.neupanesushant.kastha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neupanesushant.kastha.core.ResponseResolver
import com.neupanesushant.kastha.data.repo.UserRepo
import com.neupanesushant.kastha.domain.model.User
import com.neupanesushant.kastha.domain.model.dto.UserUpdateDTO
import com.neupanesushant.kastha.extra.Preferences
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepo: UserRepo
) : ViewModel() {

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

    fun updateUser(user: UserUpdateDTO, onSuccess: () -> Unit, onFailure: () -> Unit) =
        viewModelScope.launch {
            val response = userRepo.updateUserDetail(user)
            ResponseResolver(response, onFailure = {
                onFailure()
            }, onSuccess = {
                _userDetail.value = it
                onSuccess()
            })()
        }
}