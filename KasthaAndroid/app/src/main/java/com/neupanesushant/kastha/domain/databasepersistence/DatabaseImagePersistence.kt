package com.neupanesushant.kastha.domain.databasepersistence

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.coroutineScope


class DatabaseImagePersistence : DatabasePersistence {
    override suspend fun invoke(fromId: Int, uri: Uri): String = coroutineScope {
        val timeStamp = System.currentTimeMillis() / 100
        val ref =
            FirebaseStorage.getInstance().getReference("/images/$fromId$timeStamp")
        val deferred = CompletableDeferred<String>()
        ref.putFile(uri).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener { downloadUrl ->
                deferred.complete(downloadUrl.toString())
            }.addOnFailureListener { exception ->
                deferred.completeExceptionally(exception)
            }
        }.addOnFailureListener { exception ->
            deferred.completeExceptionally(exception)
        }
        deferred.await()
    }
}