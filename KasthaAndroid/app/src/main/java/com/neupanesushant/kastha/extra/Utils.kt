package com.neupanesushant.kastha.extra

import android.os.Build
import androidx.fragment.app.Fragment


object Utils {
    inline fun <reified T> Fragment.getParcelable(key: String): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(key, T::class.java)
        } else {
            arguments?.getParcelable(key)
        }
    }

    fun areFieldsEmpty(fields: List<String>): Boolean {
        fields.forEach {
            if (it.isEmpty()) return true
        }
        return false
    }
}