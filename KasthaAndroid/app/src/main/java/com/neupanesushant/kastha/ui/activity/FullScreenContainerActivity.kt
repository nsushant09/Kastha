package com.neupanesushant.kastha.ui.activity

import android.transition.Slide
import android.view.Gravity
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.AppConfig
import com.neupanesushant.kastha.core.BaseActivity
import com.neupanesushant.kastha.core.Router
import com.neupanesushant.kastha.databinding.ActivityFullScreenContainerBinding

class FullScreenContainerActivity : BaseActivity<ActivityFullScreenContainerBinding>() {

    companion object {
        const val FRAGMENT_ROUTE = "FRAGMENT_ROUTE"
        const val FRAGMENT_ROUTE_BUNDLE = "FRAGMENT_ROUTE_BUNDLE"
    }

    override val layoutId: Int
        get() = R.layout.activity_full_screen_container

    override fun initialize() {
        val data = intent.extras?.getBundle("data")
        if (data == null) finish()
        val route = data?.getString(FRAGMENT_ROUTE)
        val bundle = data?.getBundle(FRAGMENT_ROUTE_BUNDLE)
        if (route == null) finish()
        val router = if (bundle == null) Router(this) else Router(this, bundle)
        router.routeNoBackStack(
            R.id.fullscreen_fragment_container,
            AppConfig.getFragment(route!!)
        )
    }

    override fun setupViews() {
    }

    override fun setupEventListener() {
    }

    override fun setupObserver() {
    }

}