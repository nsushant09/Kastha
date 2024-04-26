package com.neupanesushant.kurakani.domain.usecase.message_manager

import com.neupanesushant.kastha.domain.managers.FirebaseManager

class MessageDeleter(private val fromId: Int, private val toId: Int) {
    fun delete(timeStamp: String) {
        FirebaseManager.firebaseDatabase
            .getReference("/user-messages/$fromId$toId/$fromId$timeStamp$toId")
            .removeValue()
    }
}