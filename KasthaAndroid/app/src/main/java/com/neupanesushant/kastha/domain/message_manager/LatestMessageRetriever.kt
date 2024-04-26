package com.neupanesushant.kastha.domain.message_manager

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.neupanesushant.kastha.BuildConfig
import com.neupanesushant.kastha.domain.managers.FirebaseManager
import com.neupanesushant.kastha.domain.model.chat.Message
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class LatestMessageRetriever() : ValueEventListener {

    val latestMessages: MutableStateFlow<List<Message>> = MutableStateFlow(emptyList())

    suspend fun fetch() = coroutineScope {
        FirebaseManager.firebaseDatabase.reference.child("latest-messages")
            .child(BuildConfig.ADMIN_ID.toString())
            .addValueEventListener(this@LatestMessageRetriever)
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        val data = snapshot.getValue<HashMap<String, HashMap<String, Message>>>()
//
//        CoroutineScope(Dispatchers.IO).launch {
//            latestMessages.emit(tempList)
//        }
    }

    override fun onCancelled(error: DatabaseError) {
    }
}