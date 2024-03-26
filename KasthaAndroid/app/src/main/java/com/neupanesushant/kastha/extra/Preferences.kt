package com.neupanesushant.kastha.extra

import android.content.SharedPreferences
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object Preferences : KoinComponent {

    private const val KEY_USER_ID = "USER_ID"
    private const val KEY_AUTHENTICATION_TOKEN = "AUTHENTICATION_TOKEN"
    private const val KEY_LOGGED_IN = "LOGGED_IN"


    private val preferences: SharedPreferences by inject()
    private val editor = preferences.edit()

    fun onLogIn(userId: Int, authenticationToken: String) {
        editor.putInt(KEY_USER_ID, userId)
        editor.putBoolean(KEY_LOGGED_IN, true)
        editor.putString(KEY_AUTHENTICATION_TOKEN, authenticationToken)
        editor.apply()
    }

    fun onLogOut() {
        editor.remove(KEY_USER_ID)
        editor.remove(KEY_LOGGED_IN)
        editor.remove(KEY_AUTHENTICATION_TOKEN)
        editor.apply()
    }

    fun getUserId(): Int? {
        val value = preferences.getInt(KEY_USER_ID, -1)
        return if (value != -1) value else null
    }

    fun getAuthenticationToken(): String? {
        return preferences.getString(KEY_AUTHENTICATION_TOKEN, null)
    }

    fun isUserLoggedIn() = preferences.getBoolean(KEY_LOGGED_IN, false)
}