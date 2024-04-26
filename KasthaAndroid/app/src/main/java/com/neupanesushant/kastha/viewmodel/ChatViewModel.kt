package com.neupanesushant.kastha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neupanesushant.kastha.core.ResponseResolver
import com.neupanesushant.kastha.core.State
import com.neupanesushant.kastha.data.repo.UserRepo
import com.neupanesushant.kastha.domain.message_manager.LatestMessageRetriever
import com.neupanesushant.kastha.domain.model.User
import com.neupanesushant.kastha.domain.model.chat.Message
import com.neupanesushant.kastha.extra.Preferences
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChatViewModel(
    private val userRepo: UserRepo
) : ViewModel() {

    private val _messageAndUsers = MutableLiveData<State<List<Pair<User, Message>>>>()
    val messageAndUsers: LiveData<State<List<Pair<User, Message>>>> get() = _messageAndUsers
    private val latestMessageRetriever = LatestMessageRetriever()
    private val fromId = Preferences.getUserId()

    init {
        viewModelScope.launch {
            getLatestMessages()
            latestMessageRetriever.latestMessages.collectLatest {
                val sortedMessage = it.sortedByDescending {
                    it.timeStamp
                }
                getChatUsers(sortedMessage)
            }
        }
    }

    private suspend fun getLatestMessages() {
        _messageAndUsers.value = State.Loading
        latestMessageRetriever.fetch()
    }

    private fun getChatUserIds(messages: List<Message>): List<Int> {
        val userIds = mutableListOf<Int>()
        for (message in messages) {
            if (message.fromUid == fromId.toString()) {
                message.toUid?.toIntOrNull()?.let { userIds.add(it) }
            } else {
                message.fromUid?.toIntOrNull()?.let { userIds.add(it) }
            }
        }
        return userIds
    }

    private fun getChatUsers(messages: List<Message>) = viewModelScope.launch {
        val userIds = getChatUserIds(messages)
        val response = userRepo.getChatUsers(userIds)

        val temp = mutableListOf<Pair<User, Message>>()
        ResponseResolver(response, onFailure = {
            _messageAndUsers.value = State.Error("Could not load messages")
        }, onSuccess = { users ->
            for (i in users.indices) {
                temp.add(Pair(users[i], messages[i]))
            }
            _messageAndUsers.value = State.Success(temp)
        })()
    }
}