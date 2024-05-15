package com.neupanesushant.kastha.extra

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.fragment.app.Fragment
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.UUID


object Utils {
    inline fun <reified T> Fragment.getParcelable(
        key: String,
        clazz: Class<T> = T::class.java
    ): T? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return arguments?.getParcelable(key, T::class.java)
        }

        return when (clazz) {
            String::class.java -> arguments?.getString(key) as? T
            Int::class.java -> arguments?.getInt(key) as? T
            else -> arguments?.getParcelable(key)
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

    fun Bitmap.toMultipart(formDataName: String): MultipartBody.Part {
        val byteArrayOutputStream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        val requestBody = RequestBody.create(MediaType.get("image/jpeg"), byteArray)
        val uuid = UUID.nameUUIDFromBytes(byteArray)

        return MultipartBody.Part.createFormData(
            formDataName,
            "$uuid.jpg",
            requestBody
        )
    }

    fun File.toMultipart(formDataName: String): MultipartBody.Part {
        val requestFile = RequestBody.create(MediaType.parse("model/gltf-binary"), this)
        val filePart =
            MultipartBody.Part.createFormData(
                formDataName,
                this.name,
                requestFile
            )
        return filePart
    }
}