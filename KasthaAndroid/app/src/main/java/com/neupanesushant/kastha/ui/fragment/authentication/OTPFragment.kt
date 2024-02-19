package com.neupanesushant.kastha.ui.fragment.authentication

import android.os.Parcelable
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentOtpBinding
import com.otpview.OTPListener
import kotlinx.parcelize.Parcelize

class OTPFragment : BaseFragment<FragmentOtpBinding>(), OTPListener {

    companion object {
        const val OTP_ARGUMENT = "OTPAction"
    }

    @Parcelize
    enum class OTPAction : Parcelable {
        LOGIN,
        PASSWORD_RESET
    }

    private var otpValue: String? = null
    private lateinit var otpAction: OTPAction
    override val layoutId: Int
        get() = R.layout.fragment_otp

    override fun initialize() {
        super.initialize()
        val action = arguments?.getParcelable<OTPFragment.OTPAction>(OTP_ARGUMENT)
        if (action == null) requireActivity().onBackPressedDispatcher.onBackPressed()
        otpAction = action!!
    }

    override fun setupViews() {
        binding.otpView.requestFocusOTP()
    }

    override fun setupEventListener() {
        binding.btnBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        binding.otpView.otpListener = this

        binding.btnVerify.setOnClickListener {
            if (otpAction == OTPFragment.OTPAction.LOGIN) {
            }
            if (otpAction == OTPFragment.OTPAction.PASSWORD_RESET) {

            }
        }
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