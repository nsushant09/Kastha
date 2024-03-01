package com.neupanesushant.kastha.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neupanesushant.kastha.core.NetworkResponseResolver
import com.neupanesushant.kastha.core.State
import com.neupanesushant.kastha.domain.model.OTPMailResponse
import com.neupanesushant.kastha.domain.model.dto.AuthResponse
import com.neupanesushant.kastha.domain.usecase.AuthenticationUseCase
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val authenticationUseCase: AuthenticationUseCase
) : ViewModel() {

    private var _isAuthenticationTokenReceived: MutableLiveData<State<AuthResponse>> =
        MutableLiveData(State.Default)
    val isAuthenticationTokenReceived: LiveData<State<AuthResponse>> get() = _isAuthenticationTokenReceived

    private var _oneTimePassword: MutableLiveData<OTPMailResponse?> = MutableLiveData(null)
    val oneTimePassword: LiveData<OTPMailResponse?> get() = _oneTimePassword

    fun login(email: String, password: String) {
        _isAuthenticationTokenReceived.value = State.Loading
        viewModelScope.launch {
            val response = authenticationUseCase.login(email, password)
            NetworkResponseResolver(response, onFailure = {
                Log.d("Network", "Login api failure cause : $it")
            }, onSuccess = {
                Log.d("Network", "Login api response$it")
            })
        }
    }

    fun register(firstName: String, lastName: String, email: String, password: String) {
        viewModelScope.launch {
            val response = authenticationUseCase.register(firstName, lastName, email, password)
            NetworkResponseResolver(response, onFailure = {
                Log.d("Network", "Register api failure cause : $it")
            }, onSuccess = {
                Log.d("Network", "Register api response$it")
            })
        }
    }

    fun sendOTP(email: String) {
        viewModelScope.launch {
            val response = authenticationUseCase.sendOTP(email)
            NetworkResponseResolver(response, onSuccess = {
                _oneTimePassword.value = it
            })
        }
    }

    fun resetOTP() {
        _oneTimePassword.value = null
    }

}