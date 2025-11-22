package com.neupanesushant.kastha.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.neupanesushant.kastha.extra.Preferences

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigateBasedOnLogInStatus()
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