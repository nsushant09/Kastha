package com.neupanesushant.kastha.extra.extensions

import android.os.Handler
import android.os.Looper
import androidx.viewpager.widget.ViewPager

fun ViewPager.autoScroll(interval: Long) {

    val handler = Handler(Looper.getMainLooper())
    var scrollPosition = 0

    val runnable = object : Runnable {
        override fun run() {
            val count = adapter?.count ?: 0
            setCurrentItem(scrollPosition++ % count, true)

            handler.postDelayed(this, interval)
        }
    }

    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            scrollPosition = position + 1
        }

        override fun onPageScrollStateChanged(state: Int) {}
    })

    handler.post(runnable)
}