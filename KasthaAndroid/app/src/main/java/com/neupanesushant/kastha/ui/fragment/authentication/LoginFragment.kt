package com.neupanesushant.kastha.ui.fragment.authentication

import androidx.core.os.bundleOf
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.appcore.RouteConfig
import com.neupanesushant.kastha.core.AppConfig
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.core.ResponseResolver
import com.neupanesushant.kastha.core.Router
import com.neupanesushant.kastha.databinding.FragmentLoginBinding
import com.neupanesushant.kastha.extra.helper.Validator
import com.neupanesushant.kastha.ui.dialog.DialogUtils
import com.neupanesushant.kastha.ui.dialog.LoadingDialog
import com.neupanesushant.kastha.viewmodel.AuthenticationViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val authenticationViewModel: AuthenticationViewModel by sharedViewModel()
    override val layoutId: Int
        get() = R.layout.fragment_login

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
            onSignInClick()
        }

        binding.btnForgotPassword.setOnClickListener {
            Router(requireActivity()).route(
                R.id.authentication_fragment_container,
                AppConfig.getFragment(RouteConfig.FP_EMAIL_VERFICATION_FRAGMENT)
            )
        }
    }

    override fun setupObserver() {}

    private fun onSignInClick() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (!areValidDetails(email, password)) {
            return
        }

        authenticationViewModel.validateLogin(email, password) { response ->
            ResponseResolver(response, onFailure = {
                DialogUtils.generalDialog(requireContext(), description = it)
            }, onSuccess = {
                onLoginDetailsValidated(email, password)
            })
        }
    }

    private fun onLoginDetailsValidated(email: String, password: String) {
        authenticationViewModel.sendOTP(email)
        val bundle = bundleOf(
            OTPFragment.OTP_ACTION to OTPFragment.OTPAction.LOGIN,
            OTPFragment.LOGIN_EMAIL_ARGUMENT to email,
            OTPFragment.LOGIN_PASSWORD_ARGUMENT to password
        )

        Router(requireActivity(), bundle).route(
            R.id.authentication_fragment_container,
            AppConfig.getFragment(RouteConfig.OTP_FRAGMENT),
        )
    }

    private fun areValidDetails(email: String, password: String): Boolean {
        binding.apply {
            tilEmail.error = null
            tilPassword.error = null
        }

        var allValid = true
        val emailValidation = Validator.email(email)
        val passwordValidation = Validator.password(password)

        if (!emailValidation.first) {
            binding.tilEmail.error = emailValidation.second
            allValid = false
        }

        if (!passwordValidation.first) {
            binding.tilPassword.error = passwordValidation.second
            allValid = false
        }
        return allValid
    }
}