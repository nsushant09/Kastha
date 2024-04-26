package com.neupanesushant.kastha.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.databinding.FragmentLongActionsBinding
import com.neupanesushant.kastha.domain.model.chat.Message
import com.neupanesushant.kastha.domain.model.chat.MessageType
import com.neupanesushant.kastha.domain.usecase.DownloadFileUseCase
import com.neupanesushant.kastha.domain.usecase.ShareUseCase

class LongActionsFragment() : BottomSheetDialogFragment() {

    private lateinit var _binding: FragmentLongActionsBinding
    private val binding get() = _binding

    private lateinit var message: Message
    private lateinit var onDeleteClick: (String) -> Unit

    private val downloadFileUseCase: DownloadFileUseCase by lazy {
        DownloadFileUseCase(requireContext())
    }
    private val shareUseCase: ShareUseCase by lazy {
        ShareUseCase(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogThemeNoFloating)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLongActionsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupEventListener()
        setupObserver()
    }

    private fun setupView() {
        if (message.messageType != MessageType.IMAGE) {
            binding.btnSave.isVisible = false
            binding.btnShare.isVisible = false
        }
    }

    private fun setupObserver() {
    }

    private fun setupEventListener() {
        binding.btnCancel.setOnClickListener {
            dismissAllowingStateLoss()
        }

        binding.btnSave.setOnClickListener {
            downloadFileUseCase.download(message)
            dismissAllowingStateLoss()
        }

        binding.btnShare.setOnClickListener {
            shareUseCase.share(message)
            dismissAllowingStateLoss()
        }

        binding.btnDelete.setOnClickListener {
            onDeleteClick(message.timeStamp.toString())
            dismissAllowingStateLoss()
        }
    }

    companion object {
        private var instance: LongActionsFragment? = null
        fun getInstance(message: Message, onDeleteClick: (String) -> Unit): LongActionsFragment {
            if (instance == null) {
                instance = LongActionsFragment()
            }
            instance!!.message = message
            instance!!.onDeleteClick = onDeleteClick
            return instance!!
        }
    }
}