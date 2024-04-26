package com.neupanesushant.kurakani.domain.usecase.message_manager

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.neupanesushant.kastha.domain.managers.FirebaseManager
import com.neupanesushant.kastha.domain.model.chat.Message
import kotlinx.coroutines.flow.MutableStateFlow

class ChatEventHandler(private val fromId: Int, private val toId: Int) : ChildEventListener {

    enum class ACTION {
        ADD,
        DELETE
    }

    val messageWithAction: MutableStateFlow<Pair<Message, ACTION>?> = MutableStateFlow(null)

    init {
        FirebaseManager.firebaseDatabase.getReference("/user-messages/$fromId$toId")
            .addChildEventListener(this)
    }

    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
        val newMessage = snapshot.getValue(Message::class.java) ?: return
        messageWithAction.value = Pair(newMessage, ACTION.ADD)
    }

    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
    }

    override fun onChildRemoved(snapshot: DataSnapshot) {
        val message = snapshot.getValue(Message::class.java) ?: return
        messageWithAction.value = Pair(message, ACTION.DELETE)
    }

    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
    }

    override fun onCancelled(error: DatabaseError) {
    }
}