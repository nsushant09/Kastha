package com.neupanesushant.kastha.ui.dialog

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.databinding.DialogLoadingBinding

class LoadingDialog private constructor(
    private val infoText: String = "Loading...",
    private val showBackground: Boolean = true,
    private val showInfoText: Boolean = true

) : DialogFragment() {
    private lateinit var binding: DialogLoadingBinding
    private var showRequested = false
    private val handler = Handler(Looper.getMainLooper())
    private val dismissRunnable: Runnable by lazy {
        Runnable {
            try {
                instance.dismiss()
                showRequested = false
                handler.removeCallbacksAndMessages(null)
            } catch (e: Exception) {
                handler.postDelayed(dismissRunnable, 200)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.dialog_loading,
            container,
            false
        )
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.isCancelable = false
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.dialogInfoText.text = infoText
        binding.dialogInfoText.isVisible = showInfoText
        if (!showBackground) {
            binding.llLoadingContainer.background = null
        }
    }

    fun show(fragmentManager: FragmentManager) {
        showRequested = true
        handler.postDelayed({
            if (showRequested) {
                super.show(fragmentManager, this::class.java.name)
            }
        }, 200)
    }

    fun remove() {
        handler.removeCallbacksAndMessages(null)
        handler.post(dismissRunnable)
    }

    companion object {
        val instance: LoadingDialog = LoadingDialog()
    }

}