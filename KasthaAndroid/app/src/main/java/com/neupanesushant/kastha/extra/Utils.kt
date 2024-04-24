package com.neupanesushant.kastha.extra

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import java.io.File


object Utils {
    inline fun <reified T> Fragment.getParcelable(key: String): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(key, T::class.java)
        } else {
            arguments?.getParcelable(key)
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun isStringInTarget(name: String?, target: String): Boolean {
        val lengthOfTarget = target.length
        var lengthOfName = name?.length
        if (lengthOfName == null) {
            lengthOfName = 0
        }
        val loopSize = lengthOfName - lengthOfTarget
        for (i in 0 until loopSize + 1) {
            try {
                if (name?.substring(i, lengthOfTarget + i).equals(target, true)) {
                    return true
                }
            } catch (e: Exception) {
                continue
            }
        }
        return false

    }

    fun uriToFile(context: Context, uri: Uri): File? {
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(uri, null, null, null, null)
        return cursor?.use { cursor ->
            cursor.moveToFirst()
            val filePathColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            val filePath = cursor.getString(filePathColumn)
            File(filePath)
        }
    }
}