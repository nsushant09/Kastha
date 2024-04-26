package com.neupanesushant.kastha.ui.fragment.chat.chatmessageadapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.neupanesushant.kastha.BuildConfig
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.databinding.ChatMessageToLayoutBinding

class ViewHolderToText(
    private val chatMessageAdapter: ChatMessageAdapter,
    binding: ChatMessageToLayoutBinding
) :
    ChatMessageViewHolder(binding) {

    private var profileImage: ImageView
    private var messageBody: TextView

    init {
        profileImage = binding.ivProfileImage
        messageBody = binding.tvMessageBody
    }

    override fun bind(position: Int) {

        if (position != 0 && chatMessageAdapter.list[position - 1].fromUid == chatMessageAdapter.list[position].fromUid) {
            profileImage.visibility = View.INVISIBLE
        } else {
            if (chatMessageAdapter.userId == BuildConfig.ADMIN_ID)
                Glide.with(itemView.context)
                    .load(R.drawable.user_profile)
                    .into(profileImage)
            else
                Glide.with(itemView.context)
                    .load(R.drawable.application_icon_dark)
                    .into(profileImage)
        }
        messageBody.text = chatMessageAdapter.list[position].messageBody

        itemView.setOnLongClickListener {
            chatMessageAdapter.onLongClickAction(chatMessageAdapter.list[position])
            true
        }
    }
}