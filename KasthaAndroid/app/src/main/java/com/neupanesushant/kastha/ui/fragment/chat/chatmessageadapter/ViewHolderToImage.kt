package com.neupanesushant.kastha.ui.fragment.chat.chatmessageadapter

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.neupanesushant.kastha.BuildConfig
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.databinding.ChatImageToLayoutBinding

class ViewHolderToImage(
    private val chatMessageAdapter: ChatMessageAdapter,
    binding: ChatImageToLayoutBinding
) :
    ChatMessageViewHolder(binding) {

    private var profileImage: ImageView
    private var messageBody: ImageView

    init {
        profileImage = binding.ivProfileImage
        messageBody = binding.ivChatImage
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

        Glide.with(chatMessageAdapter.context)
            .asBitmap()
            .load(chatMessageAdapter.list.get(position).messageBody)
            .apply(RequestOptions().override(480))
            .apply(RequestOptions().transform(RoundedCorners(32)))
            .into(messageBody)
        itemView.setOnLongClickListener {
            chatMessageAdapter.onLongClickAction(chatMessageAdapter.list[position])
            true
        }
    }
}