package com.neupanesushant.kastha.ui.fragment.chat

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.MotionEvent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentChatMessagingBinding
import com.neupanesushant.kastha.domain.model.User
import com.neupanesushant.kastha.domain.usecase.CameraUseCase
import com.neupanesushant.kastha.extra.Utils.getParcelable
import com.neupanesushant.kurakani.domain.usecase.audiorecorder.AndroidAudioRecorder
import com.neupanesushant.kurakani.domain.usecase.audiorecorder.AutoRunningTimer
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
    override fun initialize() {
        if (arguments == null) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        currentUserId = getParcelable<Int>(CURRENT_USER_ID) ?: 0
        otherUser = getParcelable<User>(USER_ARGUMENT)


        cameraUseCase = CameraUseCase(requireContext())
        audioRecorder = AndroidAudioRecorder(requireContext())
        autoRunningTimer = AutoRunningTimer()
    }

    override fun setupViews() {
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
            recordAudioMessageEventHandler(event)
        }
        binding.btnSend.setOnClickListener {
            if (binding.etWriteMessage.text.isNotEmpty()) {
                // TODO : SEND TEXT MESSAGE
//                viewModel.sendTextMessage(binding.etWriteMessage.text.toString())
                binding.etWriteMessage.text.clear()
            }
        }
    }

    override fun setupObserver() {
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

            // TODO : SEND IMAGE MESSAGE
//            viewModel.sendImagesMessage(tempImages)
        }
    }

    private val cameraActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        val file = File(requireContext().cacheDir, cameraUseCase.getLastCapturedFileName())
        val uri = FileProvider.getUriForFile(
            requireContext(),
            requireContext().applicationContext.packageName + ".provider",
            file
        )
        val tempImages: ArrayList<Uri> = arrayListOf()
        tempImages.add(uri)

        // TODO : SEND IMAGE MESSAGE
//        viewModel.sendImagesMessage(tempImages)
    }

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
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

    private fun audioPermissionLauncher(event: MotionEvent) =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                recordAudioMessageEventHandler(event)
            }
        }

    private fun recordAudioMessageEventHandler(event: MotionEvent): Boolean {

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.RECORD_AUDIO
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            audioPermissionLauncher(event).launch(android.Manifest.permission.RECORD_AUDIO)
            return false
        }

        if (event.action == MotionEvent.ACTION_UP)
            audioRecorder.onMotionEventUp {
                // TODO : SEND AUDIO MESSAGE
//                viewModel.sendAudioMessage(it.toUri())
                displayAudioRecording(false)
            }

        if (event.action == MotionEvent.ACTION_DOWN)
            audioRecorder.onMotionEventDown { displayAudioRecording(true) }

        return true
    }

    private fun displayAudioRecording(isRecording: Boolean) {

        binding.etWriteMessage.isCursorVisible = !isRecording
        val color =
            if (isRecording) com.google.android.material.R.attr.colorPrimaryVariant else R.color.dark_grey
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
}