package com.neupanesushant.kastha.domain.messagedeliverpolicy

import android.net.Uri
import com.neupanesushant.kastha.domain.databasepersistence.DatabaseAudioPersistence
import com.neupanesushant.kastha.domain.model.chat.Message
import com.neupanesushant.kastha.domain.model.chat.MessageType

class AudioDeliverPolicy
    : DeliverPolicy {

    private val audioPersistence = DatabaseAudioPersistence()
    override suspend fun getMessageObject(message: String, fromId: String, toId: String): Message {
        val timeStamp = System.currentTimeMillis() / 100;
        val audioUrl = audioPersistence(fromId.toInt(), Uri.parse(message))
        return Message(
            fromId,
            toId,
            MessageType.AUDIO,
            audioUrl,
            timeStamp
        )
    }
}