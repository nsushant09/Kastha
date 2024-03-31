package com.neupanesushant.kastha.ui.fragment.authentication

import android.os.Parcelable
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.core.Router
import com.neupanesushant.kastha.core.StateResolver
import com.neupanesushant.kastha.databinding.FragmentOtpBinding
import com.neupanesushant.kastha.domain.model.dto.RegisterDTO
import com.neupanesushant.kastha.extra.Utils.getParcelable
import com.neupanesushant.kastha.ui.activity.MainActivity
import com.neupanesushant.kastha.ui.dialog.DialogUtils
import com.neupanesushant.kastha.viewmodel.AuthenticationViewModel
import com.otpview.OTPListener
import kotlinx.parcelize.Parcelize
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class OTPFragment : BaseFragment<FragmentOtpBinding>(), OTPListener {

    private val authenticationViewModel by sharedViewModel<AuthenticationViewModel>()

    companion object {
        const val OTP_ACTION = "OTP_ACTION"
        const val OTP_ARGUMENT = "OTP_ARGUMENT"

        const val LOGIN_EMAIL_ARGUMENT = "LOGIN_EMAIL_ARGUMENT"
        const val LOGIN_PASSWORD_ARGUMENT = "LOGIN_PASSWORD_ARGUMENT"

        const val REGISTER_DTO_ARGUMENT = "REGISTER_DTO_ARGUMENT"
    }

    @Parcelize
    enum class OTPAction(val value: String) : Parcelable {
        LOGIN("LOGIN"), PASSWORD_RESET("PASSWORD_RESET"), REGISTER("REGISTER")
    }

    private var otpValue: String? = null
    private lateinit var otpAction: OTPAction
    override val layoutId: Int
        get() = R.layout.fragment_otp

    override fun initialize() {
        super.initialize()
        val action = getParcelable<OTPAction>(OTP_ACTION)
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
            if (binding.otpView.otp == authenticationViewModel.oneTimePassword.value?.authenticationKey) {
                onOTPVerified()
            }
        }
    }

    override fun setupObserver() {
        authenticationViewModel.isAuthenticationTokenReceived.observe(viewLifecycleOwner) { state ->
            StateResolver(state, onSuccess = {
                hideLoading()
                Router(requireActivity()).routeClearTask(MainActivity::class.java)
            }, onError = {
                hideLoading()
                DialogUtils.generalDialog(requireContext(), "Could not login. Please try again", "Login Error")
            })
        }
    }

    override fun onInteractionListener() {
        val otpContent = binding.otpView.otp
        binding.btnVerify.isEnabled = !(otpContent.isNullOrEmpty() || otpContent.length != 6)
        if (otpContent == authenticationViewModel.oneTimePassword.value?.authenticationKey) {
            onOTPVerified()
        }
    }

    override fun onOTPComplete(otp: String) {
        binding.btnVerify.isEnabled = true
        otpValue = otp
    }

    private fun onOTPVerified() {
        if (otpAction.value == OTPAction.LOGIN.value) {
            logInAction()
        }
        if (otpAction.value == OTPAction.PASSWORD_RESET.value) {
            passwordResetAction()
        }
        if (otpAction.value == OTPAction.REGISTER.value) {
            registerAction()
        }
    }

    private fun logInAction() {
        val email = getParcelable<String>(LOGIN_EMAIL_ARGUMENT)
        val password = getParcelable<String>(LOGIN_PASSWORD_ARGUMENT)
        if (email == null || password == null) {
            toast("An error occured during login")
        } else {
            authenticationViewModel.login(email, password)
        }
    }

    private fun passwordResetAction() {}
    private fun registerAction() {
        val registerDTO = getParcelable<RegisterDTO>(REGISTER_DTO_ARGUMENT)
        if (registerDTO == null) {
            toast("An error occured during registration")
        } else {
            authenticationViewModel.register(registerDTO)
        }
    }
}