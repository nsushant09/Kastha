package com.neupanesushant.kastha.core

import androidx.databinding.ViewDataBinding

sealed class State<out T> {
    data object Default : State<Nothing>()
    data object Loading : State<Nothing>()
    data class Success<T>(val data: T) : State<T>()
    data class Error(val errorMessage: String) : State<Nothing>()
}

class StateResolver<T>(
    private val state: State<T>,
    private val onSuccess: (T) -> Unit,
    private val onLoading: () -> Unit = {},
    private val onError: (String) -> Unit = {}
) {
    operator fun invoke() {
        if (state is State.Success) {
            onSuccess(state.data)
            return
        }
        if (state is State.Loading) {
            onLoading()
            return
        }
        if (state is State.Error) {
            onError(state.errorMessage)
            return
        }
    }
}