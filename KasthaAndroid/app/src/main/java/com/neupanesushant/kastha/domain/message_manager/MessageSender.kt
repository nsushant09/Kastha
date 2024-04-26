package com.neupanesushant.kastha.domain.message_manager

import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.neupanesushant.kastha.domain.model.chat.MessageType
import com.neupanesushant.kastha.extra.WorkerCodes
import com.neupanesushant.kastha.domain.messagedeliverpolicy.AudioDeliverPolicy
import com.neupanesushant.kastha.domain.messagedeliverpolicy.ImageDeliverPolicy
import com.neupanesushant.kastha.domain.messagedeliverpolicy.MessageUpdateWorker
import com.neupanesushant.kastha.domain.messagedeliverpolicy.TextDeliverPolicy
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MessageSender(
    private val fromId: Int,
    private val toId: Int
) : KoinComponent {

    private val workManager: WorkManager by inject()

    companion object {
        private val MESSAGE_TYPE_POLICIES = hashMapOf(
            Pair(MessageType.IMAGE, OneTimeWorkRequestBuilder<ImageDeliverPolicy>()),
            Pair(MessageType.TEXT, OneTimeWorkRequestBuilder<TextDeliverPolicy>()),
            Pair(MessageType.AUDIO, OneTimeWorkRequestBuilder<AudioDeliverPolicy>()),
        )
    }

    fun send(message: String, messageType: MessageType) {
        val workRequest = getWorkRequest(message, messageType) ?: return
        beginUniqueWork(workRequest)
    }

    private fun getWorkRequest(message: String, messageType: MessageType): OneTimeWorkRequest? {
        val policy = MESSAGE_TYPE_POLICIES[messageType] ?: return null

        return policy.setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).setInputData(
            Data.Builder().putString(WorkerCodes.INPUT_MESSAGE, message)
                .putString(WorkerCodes.FRIEND_UID, toId.toString())
                .putString(WorkerCodes.FROM_ID, fromId.toString())
                .build()

        ).build()
    }

    private fun beginUniqueWork(workRequest: OneTimeWorkRequest) {
        val updateWorker = OneTimeWorkRequestBuilder<MessageUpdateWorker>().setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).build()

        workManager.beginUniqueWork(
            "SEND_MESSAGE",
            ExistingWorkPolicy.APPEND,
            workRequest
        )
            .then(updateWorker)
            .enqueue()
    }


}