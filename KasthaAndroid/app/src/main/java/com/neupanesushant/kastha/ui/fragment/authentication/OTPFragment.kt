package com.neupanesushant.kastha.ui.fragment.authentication

import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentOtpBinding
import com.otpview.OTPListener

class OTPFragment : BaseFragment<FragmentOtpBinding>(), OTPListener {

    private var otpValue: String? = null
    override val layoutId: Int
        get() = R.layout.fragment_otp
    
    override fun setupViews() {
        binding.otpView.requestFocusOTP()
    }

    override fun setupEventListener() {
        binding.btnBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        binding.otpView.otpListener = this
    }

    override fun setupObserver() {
    }

    override fun onInteractionListener() {
        val otpContent = binding.otpView.otp
        binding.btnVerify.isEnabled = !(otpContent.isNullOrEmpty() || otpContent.length != 6)
    }

    override fun onOTPComplete(otp: String) {
        binding.btnVerify.isEnabled = true
        otpValue = otp
    }
}