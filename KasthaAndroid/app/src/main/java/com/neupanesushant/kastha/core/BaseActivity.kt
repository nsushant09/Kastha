package com.neupanesushant.kastha.core

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.neupanesushant.kastha.broadcast_receivers.NetworkChangeReceiver

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: T

    @get:LayoutRes
    protected abstract val layoutId: Int

    private val networkChangeReceiver = NetworkChangeReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        setup()
    }

    private fun setup() {
        initialize()
        setupViews()
        setupEventListener()
        setupObserver()
        setupExtras()
    }

    abstract fun setupViews()
    abstract fun setupEventListener()
    abstract fun setupObserver()

    protected open fun initialize() {}
    protected open fun setupExtras() {}

    fun getActivityBinding(): T = binding

    override fun onResume() {
        super.onResume()
        registerReceiver(
            networkChangeReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkChangeReceiver)
    }
}