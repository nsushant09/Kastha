package com.neupanesushant.kastha.ui.fragment.authentication

import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.appcore.RouteConfig
import com.neupanesushant.kastha.core.AppConfig
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.core.Router
import com.neupanesushant.kastha.databinding.FragmentSignupBinding
import com.neupanesushant.kastha.domain.model.dto.RegisterDTO
import com.neupanesushant.kastha.extra.Utils
import com.neupanesushant.kastha.viewmodel.AuthenticationViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignUpFragment : BaseFragment<FragmentSignupBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_signup

    private val authenticationViewModel: AuthenticationViewModel by sharedViewModel()
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
            return@addOnButtonCheckedListener
            binding.layoutStoreDetails.isVisible =
                binding.btnTraderToggle.id == checkedId && isChecked
        }

        binding.btnSignUp.setOnClickListener {
            onSignUpClick()
        }

    }

    override fun setupObserver() {}

    private fun setupGenderAutoCompleteView() {
        val options = arrayOf("Male", "Female")
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, options)
        binding.etGender.setAdapter(adapter)
    }

    private fun onSignUpClick() {
        binding.apply {
            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val password = etPassword.text.toString()
            val location = etLocation.text.toString()
            val gender = etGender.text.toString()
            val email = etEmail.text.toString()

            if (Utils.areFieldsEmpty(
                    listOf(
                        firstName,
                        lastName,
                        password,
                        location,
                        gender,
                        email
                    )
                )
            ) {
                toast("Please fill up all the fields")
                return
            }

            authenticationViewModel.sendOTP(email)
            val registerDTO = RegisterDTO(firstName, lastName, email, password, location, gender)
            val data = bundleOf(
                OTPFragment.OTP_ACTION to OTPFragment.OTPAction.REGISTER,
                OTPFragment.REGISTER_DTO_ARGUMENT to registerDTO
            )
            Router(requireActivity(), data).route(
                R.id.authentication_fragment_container,
                AppConfig.getFragment(RouteConfig.OTP_FRAGMENT)
            )
        }
    }

}