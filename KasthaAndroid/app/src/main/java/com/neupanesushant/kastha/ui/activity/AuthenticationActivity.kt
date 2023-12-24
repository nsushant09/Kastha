package com.neupanesushant.kastha.ui.activity

import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.appcore.RouteConfig
import com.neupanesushant.kastha.core.AppConfig
import com.neupanesushant.kastha.core.BaseActivity
import com.neupanesushant.kastha.core.Router
import com.neupanesushant.kastha.databinding.ActivityAuthenticationBinding

class AuthenticationActivity : BaseActivity<ActivityAuthenticationBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_authentication

    override fun setupViews() {
        Router(this).route(
            R.id.authentication_fragment_container,
            AppConfig.getFragment(RouteConfig.ONBOARDING_FRAGMENT)
        )
    }

    override fun setupEventListener() {
    }

    override fun setupObserver() {
    }

    override fun initialize() {
    }
}