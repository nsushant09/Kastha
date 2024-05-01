package com.neupanesushant.kastha.extra.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.util.TypedValue
import android.view.View
import android.view.View.OnClickListener
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import com.neupanesushant.kastha.R

fun Context.show(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showKeyboard(view: View) {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
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

@SuppressLint("ResourceType")
fun View.Snackbar(
    content: String,
    actionTitle: String? = null,
    actionClick: OnClickListener? = null,
    anchorView: View? = null
) {
    val builder = Snackbar.make(this, content, Snackbar.LENGTH_SHORT)
    builder.setAction(actionTitle, actionClick)
    builder.setAnchorView(anchorView)
    val textView =
        builder.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    val typeface = ResourcesCompat.getFont(context, R.font.outfit_regular)
    textView.typeface = typeface
    builder.show()
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

