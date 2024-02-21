package com.neupanesushant.kastha.ui.fragment.authentication

import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.appcore.RouteConfig
import com.neupanesushant.kastha.core.AppConfig
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.core.Router
import com.neupanesushant.kastha.databinding.FragmentSignupBinding
import com.neupanesushant.kastha.libraries.validator.Validator

class SignUpFragment : BaseFragment<FragmentSignupBinding>() {

    private lateinit var validator: Validator
    override val layoutId: Int
        get() = R.layout.fragment_signup

    override fun setupViews() {
        setupGenderAutoCompleteView()
    }

    override fun setupEventListener() {
        binding.btnBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }

        binding.btnSignIn.setOnClickListener {
            Router(requireActivity()).route(
                R.id.authentication_fragment_container,
                AppConfig.getFragment(RouteConfig.LOGIN_FRAGMENT)
            )
        }
        binding.toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            binding.layoutStoreDetails.isVisible =
                binding.btnTraderToggle.id == checkedId && isChecked
        }

        binding.btnSignUp.setOnClickListener {
        }
    }

    override fun setupObserver() {
    }

    private fun setupGenderAutoCompleteView() {
        val options = arrayOf("Male", "Female")
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, options)
        binding.etGender.setAdapter(adapter)
    }
}