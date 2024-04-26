package com.neupanesushant.kastha.domain.messagedeliverpolicy

import android.content.Context
import android.net.Uri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.gson.Gson
import com.neupanesushant.kastha.domain.model.chat.Message
import com.neupanesushant.kastha.domain.model.chat.MessageType
import com.neupanesushant.kastha.extra.WorkerCodes
import com.neupanesushant.kastha.domain.databasepersistence.DatabaseAudioPersistence

class AudioDeliverPolicy(
    context: Context,
    workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {

    private val audioPersistence = DatabaseAudioPersistence()
    private suspend fun getMessageObject(message: String, fromId: String, toId: String): Message {
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

    override suspend fun doWork(): Result {
        val message = inputData.getString(WorkerCodes.INPUT_MESSAGE) ?: return Result.failure()
        val fromId = inputData.getString(WorkerCodes.FROM_ID) ?: return Result.failure()
        val toId = inputData.getString(WorkerCodes.FRIEND_UID) ?: return Result.failure()
        val result = getMessageObject(message, fromId, toId)
        val resultJson = Gson().toJson(result)
        return Result.success(
            workDataOf(
                WorkerCodes.RESULT_MESSAGE to resultJson,
                WorkerCodes.FRIEND_UID to toId,
                WorkerCodes.FROM_ID to fromId,
            ),
        )
    }
}