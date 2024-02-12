package com.neupanesushant.kastha.core

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class Router {
    private val context: Activity
    private var data = Bundle()

    constructor(context: Activity) {
        this.context = context
    }

    constructor(context: Activity, data: Bundle) {
        this.context = context
        this.data = data
    }

    private fun getIntentOrNull(action: Class<out AppCompatActivity>?): Intent? {
        if (action == null) {
            Log.e("Router", "Null action provided, Routing Stopped !!!")
            return null;
        }
        val intent = Intent(context, action)
        if (!data.isEmpty) {
            intent.putExtra("data", data)
        }
        return intent
    }

    fun route(action: Class<out AppCompatActivity>?) {
        val intent = getIntentOrNull(action) ?: return
        context.startActivity(intent)
    }

    fun routeNoHistory(action: Class<out AppCompatActivity>?) {
        val intent = getIntentOrNull(action) ?: return
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NO_HISTORY
        context.startActivity(intent)
    }

    fun routeForResult(action: Class<out AppCompatActivity>?, requestCode: Int) {
        val intent = getIntentOrNull(action) ?: return
        context.startActivity(intent)
    }

    fun routeClearTask(action: Class<out AppCompatActivity>?) {
        val intent = getIntentOrNull(action) ?: return
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun route(container: Int, action: Class<out Fragment>?) {
        if (action == null) {
            Log.e("Router", "Null action provided, Routing Stopped !!!")
            return;
        }
        (context as FragmentActivity).supportFragmentManager
            .beginTransaction()
            .replace(container, action, data)
            .addToBackStack(null)
            .commit()
    }

    fun routeNoBackStack(container: Int, action: Class<out Fragment>?) {
        if (action == null) {
            Log.e("Router", "Null action provided, Routing Stopped !!!")
            return;
        }
        (context as FragmentActivity).supportFragmentManager
            .beginTransaction()
            .replace(container, action, data)
            .disallowAddToBackStack()
            .commit()
    }
}