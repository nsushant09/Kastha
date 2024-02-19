package com.neupanesushant.kastha.ui.fragment.authentication

import androidx.core.os.bundleOf
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.appcore.RouteConfig
import com.neupanesushant.kastha.core.AppConfig
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.core.Router
import com.neupanesushant.kastha.databinding.FragmentFpEmailVerificationBinding

class FPEmailVerificationFragment : BaseFragment<FragmentFpEmailVerificationBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_fp_email_verification

    override fun setupViews() {
    }

    override fun setupEventListener() {
        binding.btnBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        binding.btnVerify.setOnClickListener {
            val bundle = bundleOf(OTPFragment.OTP_ARGUMENT to OTPFragment.OTPAction.PASSWORD_RESET)
            Router(requireActivity(), bundle)
                .route(
                    R.id.authentication_fragment_container,
                    AppConfig.getFragment(RouteConfig.OTP_FRAGMENT)
                )
        }
    }

    override fun setupObserver() {
    }
}