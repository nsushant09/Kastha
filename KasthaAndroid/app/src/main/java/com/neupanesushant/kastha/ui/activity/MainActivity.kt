package com.neupanesushant.kastha.ui.activity

import android.os.Bundle
import androidx.core.os.bundleOf
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.appcore.RouteConfig
import com.neupanesushant.kastha.core.AppConfig
import com.neupanesushant.kastha.core.BaseActivity
import com.neupanesushant.kastha.core.Router
import com.neupanesushant.kastha.databinding.ActivityMainBinding
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.ui.fragment.main.ProductDetailFragment

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

    fun routeProductDetail(product: Product) {
        val routeBundle = bundleOf(
            ProductDetailFragment.PRODUCT_ARGUMENT to product
        )
        val data = bundleOf(
            FullScreenContainerActivity.FRAGMENT_ROUTE to RouteConfig.PRODUCT_DETAIL_FRAGMENT,
            FullScreenContainerActivity.FRAGMENT_ROUTE_BUNDLE to routeBundle
        )
        Router(
            this,
            data
        ).route(AppConfig.getActivity(RouteConfig.FULL_SCREEN_CONTAINER_ACTIVITY))
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
}