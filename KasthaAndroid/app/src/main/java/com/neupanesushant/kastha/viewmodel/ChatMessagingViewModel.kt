package com.neupanesushant.kastha.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neupanesushant.kastha.domain.message_manager.ChatEventHandler
import com.neupanesushant.kastha.domain.message_manager.MessageDeleter
import com.neupanesushant.kastha.domain.message_manager.MessageSender
import com.neupanesushant.kastha.domain.model.chat.Message
import com.neupanesushant.kastha.domain.model.chat.MessageType
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChatMessagingViewModel(
    private val fromId: Int, private val toId: Int
) : ViewModel() {

    private val _messages = MutableLiveData<ArrayList<Message>>()
    val messages: LiveData<ArrayList<Message>> get() = _messages

    private val messageSender = MessageSender(fromId, toId)
    private val messageDeleter = MessageDeleter(fromId, toId)
    private val chatEventHandler = ChatEventHandler(fromId, toId)

    private val tempChatLog: ArrayList<Message> = arrayListOf()

    init {
        viewModelScope.launch {
            chatEventHandler.messageWithAction.collectLatest {
                if (it == null) {
                    _messages.value = tempChatLog
                    return@collectLatest
                }
                if (it.second == ChatEventHandler.ACTION.ADD) tempChatLog.add(it.first)
                if (it.second == ChatEventHandler.ACTION.DELETE) tempChatLog.remove(it.first)
                _messages.value = tempChatLog
            }
        }
    }

    fun sendTextMessage(message: String) = viewModelScope.launch {
        messageSender.send(message, MessageType.TEXT)
    }

    fun sendAudioMessage(uri: Uri) = viewModelScope.launch {
        messageSender.send(uri.toString(), MessageType.AUDIO)
    }

    fun sendImageMessage(images: List<Uri>) = viewModelScope.launch {
        images.forEach { imageUri ->
            messageSender.send(imageUri.toString(), MessageType.IMAGE)
        }
    }

    fun deleteMessage(timeStamp: String) = viewModelScope.launch {
        messageDeleter.delete(timeStamp)
    }
}