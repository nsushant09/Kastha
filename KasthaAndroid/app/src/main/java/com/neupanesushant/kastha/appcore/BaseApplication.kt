package com.neupanesushant.kastha.appcore

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.neupanesushant.kastha.appcore.koin_module.dataModule
import com.neupanesushant.kastha.appcore.koin_module.domainModule
import com.neupanesushant.kastha.appcore.koin_module.testModule
import com.neupanesushant.kastha.appcore.koin_module.vmModule
import com.neupanesushant.kastha.core.AppConfig
import com.neupanesushant.kastha.ui.activity.AuthenticationActivity
import com.neupanesushant.kastha.ui.activity.FullScreenContainerActivity
import com.neupanesushant.kastha.ui.activity.MainActivity
import com.neupanesushant.kastha.ui.fragment.authentication.FPEmailVerificationFragment
import com.neupanesushant.kastha.ui.fragment.authentication.FPResetFragment
import com.neupanesushant.kastha.ui.fragment.authentication.LoginFragment
import com.neupanesushant.kastha.ui.fragment.authentication.OTPFragment
import com.neupanesushant.kastha.ui.fragment.authentication.OnboardingFragment
import com.neupanesushant.kastha.ui.fragment.authentication.SignUpFragment
import com.neupanesushant.kastha.ui.fragment.main.CartFragment
import com.neupanesushant.kastha.ui.fragment.main.CategoriesFragment
import com.neupanesushant.kastha.ui.fragment.main.CategoriesViewPagerFragment
import com.neupanesushant.kastha.ui.fragment.main.FavouriteFragment
import com.neupanesushant.kastha.ui.fragment.main.HomeFragment
import com.neupanesushant.kastha.ui.fragment.main.ProductDetailFragment
import com.neupanesushant.kastha.ui.fragment.main.ProfileFragment
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    private val activityMap = mutableMapOf<String, Class<out AppCompatActivity>>()
    private val fragmentMap = mutableMapOf<String, Class<out Fragment>>()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(dataModule, domainModule, vmModule, testModule)
        }
        setupActivities()
        setupFragments()
        setOnAppConfig()
    }

    private fun setupActivities() {
        activityMap.apply {
            put(RouteConfig.MAIN_ACTIVITY, MainActivity::class.java)
            put(RouteConfig.AUTHENTICATION_ACTIVITY, AuthenticationActivity::class.java)
            put(RouteConfig.FULL_SCREEN_CONTAINER_ACTIVITY, FullScreenContainerActivity::class.java)
        }
    }

    private fun setupFragments() {
        fragmentMap.apply {
            // Authentication
            put(RouteConfig.ONBOARDING_FRAGMENT, OnboardingFragment::class.java)
            put(RouteConfig.LOGIN_FRAGMENT, LoginFragment::class.java)
            put(RouteConfig.SIGN_UP_FRAGMENT, SignUpFragment::class.java)
            put(RouteConfig.OTP_FRAGMENT, OTPFragment::class.java)
            put(RouteConfig.FP_RESET_FRAGMENT, FPResetFragment::class.java)
            put(RouteConfig.FP_EMAIL_VERFICATION_FRAGMENT, FPEmailVerificationFragment::class.java)

            // Main
            put(RouteConfig.HOME_FRAGMENT, HomeFragment::class.java)
            put(RouteConfig.PROFILE_FRAGMENT, ProfileFragment::class.java)
            put(RouteConfig.CART_FRAGMENT, CartFragment::class.java)
            put(RouteConfig.CATEGORIES_FRAGMENT, CategoriesFragment::class.java)
            put(RouteConfig.CATEGORIES_VIEW_PAGER_FRAGMENT, CategoriesViewPagerFragment::class.java)
            put(RouteConfig.FAVOURITES_FRAGMENT, FavouriteFragment::class.java)
            put(RouteConfig.PRODUCT_DETAIL_FRAGMENT, ProductDetailFragment::class.java)
        }
    }

    private fun setOnAppConfig() {
        AppConfig.setActivities(activityMap)
        AppConfig.setFragments(fragmentMap)
    }
}