package com.neupanesushant.kastha.domain.managers

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.view.View
import androidx.palette.graphics.Palette
import com.neupanesushant.kastha.extra.extensions.isDarkModeOn

object PaletteManager {
    fun setBackgroundDynamically(context: Context, view: View, bitmap: Bitmap) {
        Palette.from(bitmap).generate {
            if (it == null) return@generate
            val vibrant =
                (if (context.isDarkModeOn) it.mutedSwatch else it.vibrantSwatch)
                    ?: return@generate
            view.backgroundTintList = ColorStateList.valueOf(vibrant.rgb)
            view.background.alpha = 50
        }
    }

    fun getSwatch(context: Context, bitmap: Bitmap, onSwatchGenerated: (Palette.Swatch) -> Unit) {
        Palette.from(bitmap).generate {
            if (it == null) return@generate
            val vibrant =
                (if (context.isDarkModeOn) it.mutedSwatch else it.vibrantSwatch)
                    ?: return@generate
            onSwatchGenerated(vibrant)
        }
    }
}