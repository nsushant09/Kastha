package com.neupanesushant.kastha.ui.dialog

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogUtils {
    fun generalDialog(context: Context, description: String, titleMessage: String? = null) {
        MaterialAlertDialogBuilder(context)
            .setTitle(titleMessage)
            .setMessage(description)
            .setNeutralButton("OK") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}