package com.neupanesushant.kastha.appcore.ArCore

import android.app.Activity
import com.google.ar.core.ArCoreApk
import com.google.ar.core.Config
import com.google.ar.core.Session

class ArInitializer(private val activity: Activity) {
    fun isArAvailable(): Boolean {
        val availability = ArCoreApk.getInstance().checkAvailability(activity.baseContext)
        return availability.isSupported && availability == ArCoreApk.Availability.SUPPORTED_INSTALLED
    }

    fun isArInstalled(onArCoreInstalled: () -> Unit) {
        val availability = ArCoreApk.getInstance().checkAvailability(activity.applicationContext)
        if (availability.isSupported && availability == ArCoreApk.Availability.SUPPORTED_INSTALLED) {
            onArCoreInstalled()
        } else {
            requestInstall()
        }
    }

    private fun requestInstall() {
        ArCoreApk.getInstance().requestInstall(activity, true);
    }

    fun getSession(): Session {
        val session = Session(activity.baseContext)
        val config = Config(session)
        session.configure(config)
        return session
    }
}