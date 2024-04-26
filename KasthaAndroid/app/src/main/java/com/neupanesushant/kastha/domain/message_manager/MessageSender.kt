package com.neupanesushant.kastha.domain.message_manager

import com.neupanesushant.kastha.domain.messagedeliverpolicy.AudioDeliverPolicy
import com.neupanesushant.kastha.domain.messagedeliverpolicy.ImageDeliverPolicy
import com.neupanesushant.kastha.domain.messagedeliverpolicy.MessageUpdater
import com.neupanesushant.kastha.domain.messagedeliverpolicy.TextDeliverPolicy
import com.neupanesushant.kastha.domain.model.chat.MessageType

class MessageSender(
    private val fromId: Int, private val toId: Int
) {
    private val messageUpdater = MessageUpdater()
    private val messageTypePolicies = hashMapOf(
        Pair(MessageType.IMAGE, ImageDeliverPolicy()),
        Pair(
            MessageType.TEXT, TextDeliverPolicy()
        ),
        Pair(MessageType.AUDIO, AudioDeliverPolicy()),
    )

    suspend fun send(message: String, messageType: MessageType) {
        val policy = messageTypePolicies[messageType]
        policy?.let {
            val msg = it.getMessageObject(message, fromId.toString(), toId.toString())
            messageUpdater.performUpdates(msg, fromId, toId)
        }
    }
}