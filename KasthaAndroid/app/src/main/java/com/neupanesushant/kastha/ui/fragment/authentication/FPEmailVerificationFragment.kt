package com.neupanesushant.kastha.ui.fragment.authentication

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
            Router(requireActivity())
                .route(
                    R.id.authentication_fragment_container,
                    AppConfig.getFragment(RouteConfig.FP_RESET_FRAGMENT)
                )
        }
    }

    override fun setupObserver() {
    }
}