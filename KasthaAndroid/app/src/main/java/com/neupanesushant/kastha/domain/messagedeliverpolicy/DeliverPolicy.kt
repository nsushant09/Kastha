package com.neupanesushant.kastha.domain.messagedeliverpolicy

import com.neupanesushant.kastha.domain.model.chat.Message

interface DeliverPolicy {
    suspend fun getMessageObject(message: String, fromId: String, toId: String): Message
}