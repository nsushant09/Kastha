package com.neupanesushant.kastha.extra

import android.content.SharedPreferences
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object Preferences : KoinComponent {

    private const val KEY_USER_ID = "USER_ID"
    private const val KEY_AUTHENTICATION_TOKEN = "AUTHENTICATION_TOKEN"
    private const val KEY_LOGGED_IN = "LOGGED_IN"

    private const val KEY_RECOMMENDED_CAT_1 = "RECOMMENDED_CAT_1"
    private const val KEY_RECOMMENDED_CAT_2 = "RECOMMENDED_CAT_2"
    private const val KEY_RECOMMENDED_CAT_3 = "RECOMMENDED_CAT_3"


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

    fun getUserId(): Int {
        return preferences.getInt(KEY_USER_ID, -1)
    }

    fun getAuthenticationToken(): String? {
        return preferences.getString(KEY_AUTHENTICATION_TOKEN, null)
    }

    fun isUserLoggedIn() = preferences.getBoolean(KEY_LOGGED_IN, false)

    fun getRecommendedCategories(): List<Int> {
        val cat1 = preferences.getInt(KEY_RECOMMENDED_CAT_1, -1)
        val cat2 = preferences.getInt(KEY_RECOMMENDED_CAT_2, -1)
        val cat3 = preferences.getInt(KEY_RECOMMENDED_CAT_3, -1)
        return listOf(cat1, cat2, cat3)
    }

    fun saveRecommendedCategories(categoryIds: List<Int>) {
        if (categoryIds.size >= 3) {
            editor.putInt(KEY_RECOMMENDED_CAT_3, categoryIds[2])
        }
        if (categoryIds.size >= 2) {
            editor.putInt(KEY_RECOMMENDED_CAT_2, categoryIds[1])
        }
        if (categoryIds.isNotEmpty()) {
            editor.putInt(KEY_RECOMMENDED_CAT_1, categoryIds[0])
        }
        editor.apply()
    }
}