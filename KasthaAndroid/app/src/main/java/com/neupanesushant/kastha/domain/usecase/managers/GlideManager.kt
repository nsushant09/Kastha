package com.neupanesushant.kastha.domain.usecase.managers

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

object GlideManager {
    fun loadWithBitmap(
        context: Context,
        url: String,
        onReady: (Bitmap, Transition<in Bitmap>?) -> Unit
    ) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    onReady(resource, transition)
                }
            })
    }

    fun load(context: Context, url: String, view: ImageView) {
        Glide.with(context)
            .load(url)
            .into(view)
    }
}