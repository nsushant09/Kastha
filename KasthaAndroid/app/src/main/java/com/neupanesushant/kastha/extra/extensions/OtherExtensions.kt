package com.neupanesushant.kastha.extra.extensions

import android.content.Context
import android.content.res.Resources

fun dpToPx(context: Context, dp: Float): Float {
    return dp * context.resources.displayMetrics.density
}

fun dpToPx(context: Context, dp: Int): Int {
    return (dp * context.resources.displayMetrics.density).toInt()
}

fun itemSize(context: Context, screenSizeMultiplier: Float, margin: Int) =
    ((Resources.getSystem().displayMetrics.widthPixels * screenSizeMultiplier) - dpToPx(
        context,
        margin
    )).toInt()