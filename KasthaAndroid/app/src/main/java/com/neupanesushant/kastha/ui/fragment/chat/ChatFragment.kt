package com.neupanesushant.kastha.ui.fragment.chat

import android.annotation.SuppressLint
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.neupanesushant.kastha.BuildConfig
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.appcore.RouteConfig
import com.neupanesushant.kastha.core.AppConfig
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.core.Router
import com.neupanesushant.kastha.core.StateResolver
import com.neupanesushant.kastha.databinding.FragmentChatBinding
import com.neupanesushant.kastha.databinding.ItemLatestChatBinding
import com.neupanesushant.kastha.domain.model.User
import com.neupanesushant.kastha.domain.model.chat.Message
import com.neupanesushant.kastha.domain.model.chat.MessageType
import com.neupanesushant.kastha.extra.Preferences
import com.neupanesushant.kastha.ui.adapter.RVAdapter
import com.neupanesushant.kastha.ui.dialog.DialogUtils
import com.neupanesushant.kastha.viewmodel.ChatViewModel
import org.koin.android.ext.android.inject

class ChatFragment : BaseFragment<FragmentChatBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_chat

    private val chatViewModel: ChatViewModel by inject()

    override fun setupViews() {
        binding.rvLatestMessages.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun setupEventListener() {
        binding.btnBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
    }

    override fun setupObserver() {
        chatViewModel.messageAndUsers.observe(viewLifecycleOwner) {
            StateResolver(
                it,
                onLoading = {
                    showLoading()
                },
                onSuccess = {
                    hideLoading()
                    binding.rvLatestMessages.adapter = getLatestMessageAdapter(it)
                },
                onError = {
                    hideLoading()
                    DialogUtils.generalDialog(requireContext(), it, "Error loading Chat")
                }
            )()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getLatestMessageAdapter(messagesAndUser: List<Pair<User, Message>>) =
        RVAdapter<Pair<User, Message>, ItemLatestChatBinding>(
            R.layout.item_latest_chat,
            messagesAndUser
        ) { mBinding, data, datas ->
            val user = data.first
            val message = data.second
            mBinding.tvUserName.text = user.firstName + " " + user.lastName

            if (message.messageType == MessageType.TEXT) {
                mBinding.tvLatestMessageBody.text = message.messageBody
            }

            if (message.fromUid?.toIntOrNull() == user.id) {
                if (message.messageType == MessageType.IMAGE)
                    mBinding.tvLatestMessageBody.text = "${user.firstName} sent an image"
                if (message.messageType == MessageType.AUDIO)
                    mBinding.tvLatestMessageBody.text = "${user.firstName} sent a voice message"
            } else {
                if (message.messageType == MessageType.IMAGE)
                    mBinding.tvLatestMessageBody.text = "You sent an image"
                if (message.messageType == MessageType.AUDIO)
                    mBinding.tvLatestMessageBody.text = "You sent a voice message"
            }

            mBinding.root.setOnClickListener {
                onLatestChatClick(user)
            }
        }

    private val onLatestChatClick: (otherUser: User) -> Unit =
        { otherUser ->
            //TODO Change ADMIN ID Here
            val userId = BuildConfig.ADMIN_ID
            val data = bundleOf(
                ChatMessagingFragment.CURRENT_USER_ID to userId,
                ChatMessagingFragment.USER_ARGUMENT to otherUser
            )
            Router(requireActivity(), data).route(
                R.id.fullscreen_fragment_container,
                AppConfig.getFragment(RouteConfig.CHAT_MESSAGING_FRAGMENT)
            )
        }
}