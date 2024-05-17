package com.neupanesushant.kastha.appcore

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.neupanesushant.kastha.appcore.koin_module.dataModule
import com.neupanesushant.kastha.appcore.koin_module.domainModule
import com.neupanesushant.kastha.appcore.koin_module.testModule
import com.neupanesushant.kastha.appcore.koin_module.vmModule
import com.neupanesushant.kastha.core.AppConfig
import com.neupanesushant.kastha.extra.AppContext
import com.neupanesushant.kastha.extra.Preferences
import com.neupanesushant.kastha.extra.Utils
import com.neupanesushant.kastha.ui.activity.AugmentedViewActivity
import com.neupanesushant.kastha.ui.activity.AuthenticationActivity
import com.neupanesushant.kastha.ui.activity.FullScreenContainerActivity
import com.neupanesushant.kastha.ui.activity.MainActivity
import com.neupanesushant.kastha.ui.fragment.admin.AddProductFragment
import com.neupanesushant.kastha.ui.fragment.admin.ProductEditFragment
import com.neupanesushant.kastha.ui.fragment.admin.ProfileUpdateFragment
import com.neupanesushant.kastha.ui.fragment.admin.UpdateProductFragment
import com.neupanesushant.kastha.ui.fragment.authentication.FPEmailVerificationFragment
import com.neupanesushant.kastha.ui.fragment.authentication.FPResetFragment
import com.neupanesushant.kastha.ui.fragment.authentication.LoginFragment
import com.neupanesushant.kastha.ui.fragment.authentication.OTPFragment
import com.neupanesushant.kastha.ui.fragment.authentication.OnboardingFragment
import com.neupanesushant.kastha.ui.fragment.authentication.SignUpFragment
import com.neupanesushant.kastha.ui.fragment.chat.ChatFragment
import com.neupanesushant.kastha.ui.fragment.chat.ChatMessagingFragment
import com.neupanesushant.kastha.ui.fragment.main.AugmentedViewFragment
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

        setNetworkConnectionStatus()
        navigateBasedOnLogInStatus()
    }

    private fun setupActivities() {
        activityMap.apply {
            put(RouteConfig.MAIN_ACTIVITY, MainActivity::class.java)
            put(RouteConfig.AUTHENTICATION_ACTIVITY, AuthenticationActivity::class.java)
            put(RouteConfig.FULL_SCREEN_CONTAINER_ACTIVITY, FullScreenContainerActivity::class.java)
            put(RouteConfig.AUGMENTED_VIEW_ACTIVITY, AugmentedViewActivity::class.java)
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
            put(RouteConfig.AUGMENTED_VIEW_FRAGMENT, AugmentedViewFragment::class.java)
            put(RouteConfig.CHAT_FRAGMENT, ChatFragment::class.java)
            put(RouteConfig.CHAT_MESSAGING_FRAGMENT, ChatMessagingFragment::class.java)

            // Admin
            put(RouteConfig.ADD_PRODUCT_FRAGMENT, AddProductFragment::class.java)
            put(RouteConfig.PROFILE_UPDATE_FRAGMENT, ProfileUpdateFragment::class.java)
            put(RouteConfig.PRODUCT_EDIT_FRAGMENT, ProductEditFragment::class.java)
            put(RouteConfig.UPDATE_PRODUCT_FRAGMENT, UpdateProductFragment::class.java)
        }
    }

    private fun setOnAppConfig() {
        AppConfig.setActivities(activityMap)
        AppConfig.setFragments(fragmentMap)
    }

    private fun setNetworkConnectionStatus() {
        AppContext.isOnline = Utils.isNetworkAvailable(this)
    }

    private fun navigateBasedOnLogInStatus() {
        val targetActivityClass = if (Preferences.isUserLoggedIn()) {
            Log.d("TAG", Preferences.getAuthenticationToken() ?: "No auth token")
            MainActivity::class.java
        } else {
            AuthenticationActivity::class.java
        }
        startActivity(Intent(this, targetActivityClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }
}