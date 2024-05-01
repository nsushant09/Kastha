package com.neupanesushant.kastha.ui.activity

import android.os.Bundle
import androidx.annotation.IdRes
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.appcore.RouteConfig
import com.neupanesushant.kastha.core.AppConfig
import com.neupanesushant.kastha.core.BaseActivity
import com.neupanesushant.kastha.core.Router
import com.neupanesushant.kastha.databinding.ActivityMainBinding
import com.neupanesushant.kastha.extra.Preferences
import com.neupanesushant.kastha.extra.RecommendedDataManager
import com.neupanesushant.kastha.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

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
        setupBottomNavigationItemSelectionListener()
    }

    override fun setupObserver() {
    }

    private fun setupBottomNavigationItemSelectionListener() {
        binding.bnvMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuBnvHome -> {
                    Router(this).routeNoBackStack(
                        R.id.main_fragment_container,
                        AppConfig.getFragment(RouteConfig.HOME_FRAGMENT)
                    )
                    true
                }

                R.id.menuBnvCategories -> {
                    Router(this).routeNoBackStack(
                        R.id.main_fragment_container,
                        AppConfig.getFragment(RouteConfig.CATEGORIES_FRAGMENT)
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

    fun setSelectedItem(@IdRes itemId: Int) {
        binding.bnvMain.selectedItemId = itemId
    }

    override fun onDestroy() {
        Preferences.saveRecommendedCategories(RecommendedDataManager.recommendedCategories)
        super.onDestroy()
    }
}