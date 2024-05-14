package com.neupanesushant.kastha.domain.message_manager

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.neupanesushant.kastha.BuildConfig
import com.neupanesushant.kastha.domain.managers.FirebaseManager
import com.neupanesushant.kastha.domain.model.chat.Message
import com.neupanesushant.kastha.domain.model.chat.MessageType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LatestMessageRetriever() : ValueEventListener {

    val latestMessages: MutableStateFlow<List<Message>> = MutableStateFlow(emptyList())

    suspend fun fetch() = coroutineScope {
        FirebaseManager.firebaseDatabase.reference.child("latest-messages")
            .child(BuildConfig.ADMIN_ID.toString())
            .addValueEventListener(this@LatestMessageRetriever)
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        try {
            val messages = snapshot.children.mapNotNull {
                it.getValue<Message?>()
            }.sortedByDescending { it.timeStamp }

            Log.d("DATA_TAG", messages.toString())
            CoroutineScope(Dispatchers.IO).launch {
                latestMessages.emit(messages)
            }
        } catch (e: Exception) {
            Log.d("DATA_TAG", e.stackTrace.toString())
            CoroutineScope(Dispatchers.IO).launch {
                latestMessages.emit(emptyList())
            }
        }
    }

    override fun onCancelled(error: DatabaseError) {
    }

    private fun getMessageFromSnapshot(snapshot: DataSnapshot) {
        val dataList = snapshot.value as? List<Map<String, Any>?>

        val messages = dataList?.filterNotNull()?.map { dataMap ->
                val timeStamp = dataMap["timeStamp"] as Long
                val toUid = dataMap["toUid"] as String
                val messageBody = dataMap["messageBody"] as String
                val messageTypeString = dataMap["messageType"] as String
                val messageType = when (messageTypeString) {
                    "TEXT" -> MessageType.TEXT
                    "AUDIO" -> MessageType.AUDIO
                    "IMAGE" -> MessageType.IMAGE
                    else -> throw IllegalArgumentException("Unsupported message type: $messageTypeString")
                }

                val fromUid = dataMap["fromUid"] as String
                val message = Message(fromUid, toUid, messageType, messageBody, timeStamp)
                message
            } ?: return
    }
}