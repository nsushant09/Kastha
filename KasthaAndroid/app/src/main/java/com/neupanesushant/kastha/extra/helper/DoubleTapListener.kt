package com.neupanesushant.kastha.extra.helper

import android.view.GestureDetector
import android.view.MotionEvent

class DoubleTapListener(
    private val callback: () -> Unit
) : GestureDetector.SimpleOnGestureListener() {
    override fun onDoubleTap(e: MotionEvent): Boolean {
        callback()
        return true
    }
}