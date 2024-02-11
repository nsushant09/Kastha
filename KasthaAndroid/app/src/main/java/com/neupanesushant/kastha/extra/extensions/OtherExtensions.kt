package com.neupanesushant.kastha.extra.extensions

import android.content.Context

fun dpToPx(context: Context, dp: Float): Float {
    return dp * context.resources.displayMetrics.density
}