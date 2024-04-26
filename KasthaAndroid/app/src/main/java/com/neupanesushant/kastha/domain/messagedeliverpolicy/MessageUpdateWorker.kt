package com.neupanesushant.kurakani.domain.usecase.messagedeliverpolicy

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.neupanesushant.kastha.domain.managers.FirebaseManager
import com.neupanesushant.kastha.domain.model.chat.Message
import com.neupanesushant.kastha.extra.WorkerCodes
import kotlinx.coroutines.coroutineScope

class MessageUpdateWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    //    private lateinit var notification: MessagingNotification
    override suspend fun doWork(): Result {
//        setupNotification()
        val message = getMessageFromJson(inputData.getString(WorkerCodes.RESULT_MESSAGE) ?: "")
        performUpdates(message)
        return Result.success()
    }

//    private fun setupNotification() {
//        val fcmToken = inputData.getString(WorkerCodes.FRIEND_FCM_TOKEN)
//        notification = MessagingNotification(fcmToken ?: "")
//    }

    private fun getMessageFromJson(message: String): Message {
        return Gson().fromJson(message, Message::class.java)
    }

    private suspend fun performUpdates(message: Message) = coroutineScope {
        val toId = inputData.getString(WorkerCodes.FRIEND_UID)
        val fromId = inputData.getString(WorkerCodes.FROM_ID)
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