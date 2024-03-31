package com.neupanesushant.kastha.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.databinding.DialogLoadingBinding

class LoadingDialog private constructor(
    private val infoText: String = "Loading...",
    private val showBackground: Boolean = true,
    private val showInfoText: Boolean = true

) : DialogFragment() {
    private lateinit var binding: DialogLoadingBinding
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

    override fun dismiss() {
        if (this.isVisible)
            super.dismiss()
    }

    companion object {
        val instance: LoadingDialog get() = LoadingDialog()
    }

}