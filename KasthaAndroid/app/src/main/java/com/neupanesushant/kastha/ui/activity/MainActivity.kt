package com.neupanesushant.kastha.ui.activity

import android.os.Bundle
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.appcore.RouteConfig
import com.neupanesushant.kastha.core.AppConfig
import com.neupanesushant.kastha.core.BaseActivity
import com.neupanesushant.kastha.core.Router
import com.neupanesushant.kastha.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.bnvMain.selectedItemId = R.id.menuBnvHome
    }

    override fun setupViews() {
    }

    override fun setupEventListener() {
        binding.bnvMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuBnvHome -> {
                    Router(this).routeNoBackStack(
                        R.id.main_fragment_container,
                        AppConfig.getFragment(RouteConfig.HOME_FRAGMENT)
                    )
                    true
                }

                R.id.menuBnvCart -> {
                    Router(this).routeNoBackStack(
                        R.id.main_fragment_container,
                        AppConfig.getFragment(RouteConfig.CART_FRAGMENT)
                    )
                    true
                }

                R.id.menuBnvProfile -> {
                    Router(this).routeNoBackStack(
                        R.id.main_fragment_container,
                        AppConfig.getFragment(RouteConfig.PROFILE_FRAGMENT)
                    )
                    true
                }

                else -> false
            }
        }
    }

    override fun setupObserver() {
    }
}