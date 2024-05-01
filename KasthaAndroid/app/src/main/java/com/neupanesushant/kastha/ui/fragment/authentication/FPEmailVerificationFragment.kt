package com.neupanesushant.kastha.ui.fragment.authentication

import androidx.core.os.bundleOf
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.appcore.RouteConfig
import com.neupanesushant.kastha.core.AppConfig
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.core.Router
import com.neupanesushant.kastha.databinding.FragmentFpEmailVerificationBinding
import com.neupanesushant.kastha.extra.helper.Validator
import com.neupanesushant.kastha.viewmodel.AuthenticationViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FPEmailVerificationFragment : BaseFragment<FragmentFpEmailVerificationBinding>() {

    private val authenticationViewModel: AuthenticationViewModel by sharedViewModel()
    override val layoutId: Int
        get() = R.layout.fragment_fp_email_verification

    override fun setupViews() {
    }

    override fun setupEventListener() {
        binding.btnBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }

        binding.btnVerify.setOnClickListener {
            validateAndProceed()
        }
    }

    private fun validateAndProceed() {

        val email = binding.etEmail.text.toString()
        if (!areValidDetails(email)) {
            return
        }

        authenticationViewModel.sendPasswordResetOTP(email)
        val bundle = bundleOf(
            OTPFragment.OTP_ACTION to OTPFragment.OTPAction.PASSWORD_RESET,
            OTPFragment.EMAIL_ARGUMENT to email
        )
        Router(requireActivity(), bundle)
            .route(
                R.id.authentication_fragment_container,
                AppConfig.getFragment(RouteConfig.OTP_FRAGMENT)
            )
    }

    private fun areValidDetails(email: String): Boolean {
        binding.apply {
            tilEmail.error = null
        }

        var allValid = true
        val emailValidation = Validator.email(email)

        if (!emailValidation.first) {
            binding.tilEmail.error = emailValidation.second
            allValid = false
        }

        return allValid
    }


    override fun setupObserver() {
    }
}