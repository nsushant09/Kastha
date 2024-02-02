package com.neupanesushant.kastha.ui.adapter

import androidx.databinding.ViewDataBinding

interface AdapterBindingCallback<T, VB : ViewDataBinding>{
    fun onBind(binding : VB, data : T, datas : List<T>)
}