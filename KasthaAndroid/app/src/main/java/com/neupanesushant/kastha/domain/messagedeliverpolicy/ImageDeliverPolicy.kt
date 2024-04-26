package com.neupanesushant.kastha.domain.messagedeliverpolicy

import android.net.Uri
import com.neupanesushant.kastha.domain.databasepersistence.DatabaseImagePersistence
import com.neupanesushant.kastha.domain.model.chat.Message
import com.neupanesushant.kastha.domain.model.chat.MessageType

class ImageDeliverPolicy
    : DeliverPolicy {

    private val imagePersistence = DatabaseImagePersistence()
    override suspend fun getMessageObject(message: String, fromId: String, toId: String): Message {
        val timeStamp = System.currentTimeMillis() / 100;
        val imageUrl = imagePersistence(fromId.toInt(), Uri.parse(message))
        return Message(
            fromId,
            toId,
            MessageType.IMAGE,
            imageUrl,
            timeStamp
        )
    }
}