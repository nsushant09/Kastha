package com.neupanesushant.kastha.ui.fragment.authentication

import androidx.core.os.bundleOf
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.appcore.RouteConfig
import com.neupanesushant.kastha.core.AppConfig
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.core.Router
import com.neupanesushant.kastha.core.StateResolver
import com.neupanesushant.kastha.databinding.FragmentLoginBinding
import com.neupanesushant.kastha.domain.model.dto.AuthResponse
import com.neupanesushant.kastha.extra.extensions.show
import com.neupanesushant.kastha.viewmodel.AuthenticationViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val authenticationViewModel: AuthenticationViewModel by sharedViewModel()
    override val layoutId: Int
        get() = R.layout.fragment_login


    private var logInEmail = ""
    private var logInPassword = ""
    override fun setupViews() {
    }

    override fun setupEventListener() {
        binding.btnBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }

        binding.btnSignUp.setOnClickListener {
            Router(requireActivity()).route(
                R.id.authentication_fragment_container,
                AppConfig.getFragment(RouteConfig.SIGN_UP_FRAGMENT)
            )
        }

        binding.btnSignIn.setOnClickListener {
            logInEmail = binding.etEmail.text.toString()
            logInPassword = binding.etPassword.text.toString()
            authenticationViewModel.sendOTP(binding.etEmail.text.toString())
        }

        binding.btnForgotPassword.setOnClickListener {
            Router(requireActivity()).route(
                R.id.authentication_fragment_container,
                AppConfig.getFragment(RouteConfig.FP_EMAIL_VERFICATION_FRAGMENT)
            )
        }
    }

    override fun setupObserver() {
        authenticationViewModel.isAuthenticationTokenReceived.observe(viewLifecycleOwner) {
            StateResolver<AuthResponse>(it, onSuccess = {
                onOTPSent()
            }, onError = {
                requireContext().show("Could not log in")
            })
        }
    }

    private fun onOTPSent() {
        val bundle = bundleOf(
            OTPFragment.OTPAction.LOGIN.value to OTPFragment.OTP_ACTION
        )

        Router(requireActivity(), bundle).route(
            R.id.authentication_fragment_container,
            AppConfig.getFragment(RouteConfig.OTP_FRAGMENT),
        )
    }
}