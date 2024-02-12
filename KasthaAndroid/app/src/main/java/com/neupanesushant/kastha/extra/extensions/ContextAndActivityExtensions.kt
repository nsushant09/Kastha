package com.neupanesushant.kastha.extra.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

fun Context.show(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.hideKeyboard() {
    val view = (this as Activity).currentFocus
    view?.let {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Context.hideKeyboard(view: View) {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (imm.isAcceptingText) {
        imm.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}

@ColorInt
fun Context.getColorFromAttr(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}


val Activity.isKeyboardOpen: Boolean
    get() {
        val view = this.currentFocus
        view?.let {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        return false
    }

val Context.isDarkModeOn: Boolean
    get() {
        val darkModeFlags = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return darkModeFlags == Configuration.UI_MODE_NIGHT_YES
    }