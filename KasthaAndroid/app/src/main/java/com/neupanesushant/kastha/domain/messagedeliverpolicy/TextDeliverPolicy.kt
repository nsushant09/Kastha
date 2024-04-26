package com.neupanesushant.kastha.domain.messagedeliverpolicy

import com.neupanesushant.kastha.domain.model.chat.Message
import com.neupanesushant.kastha.domain.model.chat.MessageType

class TextDeliverPolicy
    : DeliverPolicy
//    (context: Context, workerParameters: WorkerParameters)
//    : CoroutineWorker(context, workerParameters)
{
    override suspend fun getMessageObject(message: String, fromId: String, toId: String): Message {
        val timeStamp = System.currentTimeMillis() / 100
        return Message(
            fromId,
            toId,
            MessageType.TEXT,
            message,
            timeStamp
        )
    }

//    override suspend fun doWork(): Result {
//        val message = inputData.getString(WorkerCodes.INPUT_MESSAGE) ?: return Result.failure()
//        val fromId = inputData.getString(WorkerCodes.FROM_ID) ?: return Result.failure()
//        val toId = inputData.getString(WorkerCodes.FRIEND_UID) ?: return Result.failure()
//        val result = getMessageObject(message, fromId, toId)
//        val resultJson = Gson().toJson(result)
//        return Result.success(
//            workDataOf(
//                WorkerCodes.RESULT_MESSAGE to resultJson,
//                WorkerCodes.FRIEND_UID to toId,
//                WorkerCodes.FROM_ID to fromId,
//            ),
//        )
//    }
}