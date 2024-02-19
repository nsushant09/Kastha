package com.neupanesushant.kastha.ui.fragment.authentication

import androidx.core.os.bundleOf
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.appcore.RouteConfig
import com.neupanesushant.kastha.core.AppConfig
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.core.Router
import com.neupanesushant.kastha.databinding.FragmentLoginBinding
import com.neupanesushant.kastha.viewmodel.AuthenticationViewModel
import org.koin.android.ext.android.inject

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val authenticationViewModel: AuthenticationViewModel by inject()
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
            authenticationViewModel.login(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
            val bundle = bundleOf(OTPFragment.OTP_ARGUMENT to OTPFragment.OTPAction.LOGIN)
            Router(requireActivity(), bundle).route(
                R.id.authentication_fragment_container,
                AppConfig.getFragment(RouteConfig.OTP_FRAGMENT),
            )
        }

        binding.btnForgotPassword.setOnClickListener {
            Router(requireActivity()).route(
                R.id.authentication_fragment_container,
                AppConfig.getFragment(RouteConfig.FP_EMAIL_VERFICATION_FRAGMENT)
            )
        }
    }

    override fun setupObserver() {
    }
}