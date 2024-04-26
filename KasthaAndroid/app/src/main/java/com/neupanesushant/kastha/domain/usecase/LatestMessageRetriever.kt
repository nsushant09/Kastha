package com.neupanesushant.kastha.domain.usecase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.neupanesushant.kastha.domain.managers.FirebaseManager
import com.neupanesushant.kastha.domain.model.chat.Message
import com.neupanesushant.kastha.extra.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LatestMessageRetriever() : ValueEventListener {

    val latestMessages: MutableStateFlow<List<Message>> = MutableStateFlow(emptyList())

    suspend fun fetch() = coroutineScope {
        FirebaseManager.firebaseDatabase.reference.child("latest-messages")
            .child(Preferences.getUserId().toString())
            .addValueEventListener(this@LatestMessageRetriever)
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        val tempList =
            snapshot.getValue<HashMap<String, Message>>()
                ?.values
                ?.sortedByDescending { it.timeStamp }
                ?: return

        CoroutineScope(Dispatchers.IO).launch {
            latestMessages.emit(
                tempList
            )
        }
    }

    override fun onCancelled(error: DatabaseError) {
    }
}