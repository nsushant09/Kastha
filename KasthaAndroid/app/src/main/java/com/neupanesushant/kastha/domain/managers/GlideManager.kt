package com.neupanesushant.kastha.domain.managers

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.neupanesushant.kastha.BuildConfig
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.extra.extensions.show

object GlideManager {
    fun loadWithBitmap(
        context: Context,
        url: String,
        onReady: (Bitmap, Transition<in Bitmap>?) -> Unit
    ) {
        Glide.with(context)
            .asBitmap()
            .load(BuildConfig.BASE_URL + url)
            .placeholder(R.drawable.image_placeholder)
            .override(300)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    onReady(resource, transition)
                }
            })

    }

    fun load(context: Context, url: String, view: ImageView, size: Int = 200) {
        Glide.with(context)
            .load(BuildConfig.BASE_URL + url)
            .placeholder(R.drawable.image_placeholder)
            .override(size)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(view)
    }

    @SuppressLint("CheckResult")
    fun loadWithListener(
        context: Context,
        url: String,
        onSuccess: (Drawable) -> Unit,
        onError: (String) -> Unit = { context.show(it) }
    ) {
        Glide.with(context)
            .load(url)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    onError(e?.message ?: "Glide Failed to load drawable")
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    onSuccess(resource)
                    return true
                }

            })
    }
}