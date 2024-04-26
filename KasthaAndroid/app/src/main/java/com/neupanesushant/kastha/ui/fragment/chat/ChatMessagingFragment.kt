package com.neupanesushant.kastha.ui.fragment.chat

import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentChatMessagingBinding
import com.neupanesushant.kastha.domain.model.User
import com.neupanesushant.kastha.extra.Utils.getParcelable

class ChatMessagingFragment : BaseFragment<FragmentChatMessagingBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_chat_messaging

    companion object {
        const val CURRENT_USER_ID = "CURRENT_USER_ID"
        const val USER_ARGUMENT = "USER_ARGUMENT"
    }

    private var currentUserId: Int = 0
    private var otherUser: User? = null
    override fun initialize() {
        if (arguments == null) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        currentUserId = getParcelable<Int>(CURRENT_USER_ID) ?: 0
        otherUser = getParcelable<User>(USER_ARGUMENT)

    }

    override fun setupViews() {
    }

    override fun setupEventListener() {
    }

    override fun setupObserver() {
    }
}