package com.neupanesushant.kastha.ui.fragment.chat

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.MotionEvent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.neupanesushant.kastha.BuildConfig
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentChatMessagingBinding
import com.neupanesushant.kastha.domain.model.User
import com.neupanesushant.kastha.domain.model.chat.Message
import com.neupanesushant.kastha.domain.usecase.CameraUseCase
import com.neupanesushant.kastha.domain.usecase.audiorecorder.AndroidAudioRecorder
import com.neupanesushant.kastha.domain.usecase.audiorecorder.AutoRunningTimer
import com.neupanesushant.kastha.extra.Preferences
import com.neupanesushant.kastha.extra.Utils.getParcelable
import com.neupanesushant.kastha.ui.dialog.LongActionsFragment
import com.neupanesushant.kastha.ui.fragment.chat.chatmessageadapter.ChatMessageAdapter
import com.neupanesushant.kastha.viewmodel.ChatMessagingViewModel
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf
import java.io.File

class ChatMessagingFragment : BaseFragment<FragmentChatMessagingBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_chat_messaging

    private lateinit var cameraUseCase: CameraUseCase
    private lateinit var audioRecorder: AndroidAudioRecorder
    private lateinit var autoRunningTimer: AutoRunningTimer

    companion object {
        const val CURRENT_USER_ID = "CURRENT_USER_ID"
        const val USER_ARGUMENT = "USER_ARGUMENT"
    }

    private var currentUserId: Int = 0
    private var otherUser: User? = null
    private lateinit var chatMessagingViewModel: ChatMessagingViewModel
    override fun initialize() {
        if (arguments == null) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        currentUserId = getParcelable<Int>(CURRENT_USER_ID) ?: 0
        otherUser = getParcelable<User>(USER_ARGUMENT)


        cameraUseCase = CameraUseCase(requireContext())
        audioRecorder = AndroidAudioRecorder(requireContext())
        autoRunningTimer = AutoRunningTimer()

        val otherUserId = if (otherUser == null) BuildConfig.ADMIN_ID else otherUser!!.id
        chatMessagingViewModel = get { parametersOf(currentUserId, otherUserId) }
    }

    override fun setupViews() {
        showLoading()
        setOtherUserDetail(otherUser)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setupEventListener() {
        binding.btnBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        binding.ivSelectImage.setOnClickListener { chooseImage() }
        binding.cardViewAddImageIcon.setOnClickListener { requestCameraPermission() }
        binding.etWriteMessage.addTextChangedListener {
            isMessageWritten(!it.isNullOrEmpty())
        }
        binding.ivRecordAudioMessage.setOnTouchListener { _, event ->
//            toast("This feature will be added soon")
//            return@setOnTouchListener true
            requestAudioPermission(event)
            true
        }
        binding.btnSend.setOnClickListener {
            if (binding.etWriteMessage.text.isNullOrEmpty()) {
                chatMessagingViewModel.sendTextMessage(binding.etWriteMessage.text.toString())
                binding.etWriteMessage.text?.clear()
            }
        }
    }

    override fun setupObserver() {
        chatMessagingViewModel.messages.observe(viewLifecycleOwner) {
            hideLoading()
            if (it.isNullOrEmpty()) {
                return@observe
            }
            setChatData(it)
        }
    }

    private fun setChatData(messages: List<Message>) {
        binding.rvChatContent.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
        binding.rvChatContent.adapter =
            ChatMessageAdapter(
                requireContext(),
                Preferences.getUserId(),
                messages,
                onLongClickAction
            )
    }

    private val onLongClickAction: (Message) -> Unit = { message ->
        LongActionsFragment.getInstance(message) { timeStamp ->
            chatMessagingViewModel.deleteMessage(timeStamp)
        }.show(parentFragmentManager, LongActionsFragment::class.java.name)
    }

    private fun chooseImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        imageSelectorRequestLauncher.launch(Intent.createChooser(intent, "Select images"))
    }

    private val imageSelectorRequestLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
        val data = result.data ?: return@registerForActivityResult
        val tempImages: ArrayList<Uri> = arrayListOf()
        if (data.clipData != null) {
            for (i in 0 until data.clipData!!.itemCount) {
                tempImages.add(data.clipData!!.getItemAt(i).uri)
            }
            chatMessagingViewModel.sendImageMessage(tempImages)
        }
    }

    private val cameraActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        val file = File(requireContext().cacheDir, cameraUseCase.getLastCapturedFileName())
        val uri = FileProvider.getUriForFile(
            requireContext(), requireContext().applicationContext.packageName + ".provider", file
        )
        val tempImages: ArrayList<Uri> = arrayListOf()
        tempImages.add(uri)
        chatMessagingViewModel.sendImageMessage(tempImages)
    }

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            onCameraPermissionGranted()
        } else {
            cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                onCameraPermissionGranted()
            }
        }

    private fun onCameraPermissionGranted() {
        cameraActivityLauncher.launch(cameraUseCase.getCaptureImageIntent())
    }

    private fun requestAudioPermission(event: MotionEvent) {

        if (ContextCompat.checkSelfPermission(
                requireContext(), android.Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.RECORD_AUDIO),
                100110342
            )
        } else {
            onAudioPermissionGranted(event)
        }
    }


    private fun onAudioPermissionGranted(event: MotionEvent) {
        if (event.action == MotionEvent.ACTION_UP) audioRecorder.onMotionEventUp {
            chatMessagingViewModel.sendAudioMessage(it.toUri())
            displayAudioRecording(false)
        }

        if (event.action == MotionEvent.ACTION_DOWN) audioRecorder.onMotionEventDown {
            displayAudioRecording(
                true
            )
        }
    }

    private fun displayAudioRecording(isRecording: Boolean) {

        binding.etWriteMessage.isCursorVisible = !isRecording
        val color =
            if (isRecording) R.color.green_30 else R.color.dark_grey
        binding.etWriteMessage.setHintTextColor(ContextCompat.getColor(requireContext(), color))

        if (isRecording) {
            autoRunningTimer.getPrettyTime { time ->
                binding.etWriteMessage.hint = time
            }
        } else {
            binding.etWriteMessage.hint = getString(R.string.message)
            autoRunningTimer.resetTime()
        }
    }

    private fun isMessageWritten(boolean: Boolean) {
        binding.apply {
            btnSend.isVisible = boolean
            ivSelectImage.isVisible = !boolean
            ivRecordAudioMessage.isVisible = !boolean
        }
    }

    private fun setOtherUserDetail(user: User? = null) {
        if (user == null) {
            binding.tvFriendFirstName.text =
                ContextCompat.getString(requireContext(), R.string.app_name)
            Glide.with(requireContext()).load(R.drawable.application_icon_dark)
                .into(binding.ivFriendProfileImage)
        } else {
            binding.tvFriendFirstName.text = user.firstName
        }
    }
}