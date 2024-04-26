package com.neupanesushant.kastha.domain.messagedeliverpolicy

import com.neupanesushant.kastha.domain.managers.FirebaseManager
import com.neupanesushant.kastha.domain.model.chat.Message
import kotlinx.coroutines.coroutineScope

class MessageUpdater {
    suspend fun performUpdates(message: Message, fromId: Int, toId: Int) = coroutineScope {
        val timeStamp = message.timeStamp

        val fromMessagePath = "/user-messages/$fromId$toId/$fromId$timeStamp$toId"
        val toMessagePath = "/user-messages/$toId$fromId/$toId$timeStamp$fromId"
        val latestMessagePathFrom = "/latest-messages/$fromId/$toId"
        val latestMessagePathTo = "/latest-messages/$toId/$fromId"

        val updates = mapOf(
            fromMessagePath to message,
            toMessagePath to message,
            latestMessagePathFrom to message,
            latestMessagePathTo to message
        )

        FirebaseManager.firebaseDatabase.reference.updateChildren(updates)
    }
}