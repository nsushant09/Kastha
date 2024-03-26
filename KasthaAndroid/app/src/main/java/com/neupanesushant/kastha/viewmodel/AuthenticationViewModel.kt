package com.neupanesushant.kastha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neupanesushant.kastha.core.ResponseResolver
import com.neupanesushant.kastha.core.State
import com.neupanesushant.kastha.domain.model.OTPMailResponse
import com.neupanesushant.kastha.domain.model.dto.AuthResponse
import com.neupanesushant.kastha.domain.model.dto.RegisterDTO
import com.neupanesushant.kastha.domain.usecase.AuthenticationUseCase
import com.neupanesushant.kastha.extra.Preferences
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val authenticationUseCase: AuthenticationUseCase
) : ViewModel() {

    private var _isAuthenticationTokenReceived: MutableLiveData<State<AuthResponse>> =
        MutableLiveData(State.Default)
    val isAuthenticationTokenReceived: LiveData<State<AuthResponse>> get() = _isAuthenticationTokenReceived

    private var _oneTimePassword: MutableLiveData<OTPMailResponse> = MutableLiveData()
    val oneTimePassword: LiveData<OTPMailResponse> get() = _oneTimePassword

    fun login(email: String, password: String) {
        _isAuthenticationTokenReceived.value = State.Loading
        viewModelScope.launch {
            val response = authenticationUseCase.login(email, password)
            ResponseResolver(response, onFailure = {
                _isAuthenticationTokenReceived.value = State.Error(it)
            }, onSuccess = {
                _isAuthenticationTokenReceived.value = State.Success(it)
                Preferences.onLogIn(
                    userId = it.userId,
                    authenticationToken = it.tokenType + it.accessToken
                )
            })
        }
    }

    fun register(
        registerDTO: RegisterDTO
    ) {
        _isAuthenticationTokenReceived.value = State.Loading
        viewModelScope.launch {
            val response = authenticationUseCase.register(registerDTO)
            ResponseResolver(response, onFailure = {
                _isAuthenticationTokenReceived.value = State.Error(it)
            }, onSuccess = {
                _isAuthenticationTokenReceived.value = State.Success(it)
                Preferences.onLogIn(
                    userId = it.userId,
                    authenticationToken = it.tokenType + it.accessToken
                )
            })
        }
    }

    fun sendOTP(email: String) {
        viewModelScope.launch {
            val response = authenticationUseCase.sendOTP(email)
            ResponseResolver(response, onSuccess = {
                _oneTimePassword.value = it
            })
        }
    }


    fun resetOTP() {
        _oneTimePassword.value = null
    }

}