package com.neupanesushant.kastha.ui.fragment.chat.chatmessageadapter

import android.widget.TextView
import com.neupanesushant.kastha.databinding.ChatMessageFromLayoutBinding

class ViewHolderFromText(
    private val chatMessageAdapter: ChatMessageAdapter,
    binding: ChatMessageFromLayoutBinding
) :
    ChatMessageViewHolder(binding) {

    private var messageBody: TextView

    init {
        messageBody = binding.tvMessageBody
    }

    override fun bind(position: Int) {

        messageBody.text = chatMessageAdapter.list.get(position).messageBody

        itemView.setOnLongClickListener {
            chatMessageAdapter.onLongClickAction(chatMessageAdapter.list[position])
            true
        }
    }

}