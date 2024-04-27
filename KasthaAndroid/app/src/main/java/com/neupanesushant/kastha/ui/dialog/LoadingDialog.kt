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

    private val dismissHandler = Handler(Looper.getMainLooper())
    private val dismissRunnable: Runnable by lazy {
        Runnable {
            try {
                instance.dismiss()
            } catch (e: Exception) {
                dismissHandler.postDelayed(dismissRunnable, 500)
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
        this.show(fragmentManager, this::class.java.name)
    }

    fun remove() {
        dismissHandler.post(dismissRunnable)
    }

    companion object {
        val instance: LoadingDialog = LoadingDialog()
    }

}