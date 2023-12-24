package com.neupanesushant.kastha.appcore

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.neupanesushant.kastha.core.AppConfig
import com.neupanesushant.kastha.ui.activity.AuthenticationActivity
import com.neupanesushant.kastha.ui.activity.MainActivity
import com.neupanesushant.kastha.ui.fragment.OnboardingFragment

class BaseApplication : Application() {
    private val activityMap = mutableMapOf<String, Class<out AppCompatActivity>>()
    private val fragmentMap = mutableMapOf<String, Class<out Fragment>>()

    override fun onCreate() {
        super.onCreate()

        setupActivities()
        setupFragments()
        setOnAppConfig()
    }

    private fun setupActivities() {
        activityMap.apply {
            put(RouteConfig.MAIN_ACTIVITY, MainActivity::class.java)
            put(RouteConfig.AUTHENTICATION_ACTIVITY, AuthenticationActivity::class.java)
        }
    }

    private fun setupFragments() {
        fragmentMap.apply {
            put(RouteConfig.ONBOARDING_FRAGMENT, OnboardingFragment::class.java)
        }
    }

    private fun setOnAppConfig() {
        AppConfig.setActivities(activityMap)
        AppConfig.setFragments(fragmentMap)
    }
}