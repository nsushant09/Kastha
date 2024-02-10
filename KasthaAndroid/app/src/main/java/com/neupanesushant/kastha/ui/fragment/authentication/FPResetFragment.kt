package com.neupanesushant.kastha.ui.fragment.authentication

import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentFpResetBinding

class FPResetFragment : BaseFragment<FragmentFpResetBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_fp_reset

    override fun setupViews() {
    }

    override fun setupEventListener() {
        binding.btnBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
    }

    override fun setupObserver() {
    }
}