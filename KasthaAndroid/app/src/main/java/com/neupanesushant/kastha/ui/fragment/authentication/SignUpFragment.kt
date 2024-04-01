package com.neupanesushant.kastha.ui.fragment.authentication

import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.appcore.RouteConfig
import com.neupanesushant.kastha.core.AppConfig
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.core.Router
import com.neupanesushant.kastha.databinding.FragmentSignupBinding
import com.neupanesushant.kastha.domain.model.dto.RegisterDTO
import com.neupanesushant.kastha.extra.helper.Validator
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
//            binding.layoutStoreDetails.isVisible =
//                binding.btnTraderToggle.id == checkedId && isChecked
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

            if (!areValidDetails(firstName, lastName, email, password, location, gender)) {
                return
            }

            authenticationViewModel.sendOTP("nsushant09@gmail.com")
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

    private fun areValidDetails(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        location: String,
        gender: String
    ): Boolean {
        // Clear any previous errors
        binding.apply {
            tilFirstName.error = null
            tilLastName.error = null
            tilEmail.error = null
            tilPassword.error = null
            tilLocation.error = null
            tilGender.error = null
        }

        var allValid = true

        val firstNameValidation = Validator.generalString(firstName)
        if (!firstNameValidation.first) {
            binding.tilFirstName.error = firstNameValidation.second
            allValid = false
        }

        val lastNameValidation = Validator.generalString(lastName)
        if (!lastNameValidation.first) {
            binding.tilLastName.error = lastNameValidation.second
            allValid = false
        }

        val emailValidation = Validator.email(email)
        if (!emailValidation.first) {
            binding.tilEmail.error = emailValidation.second
            allValid = false
        }

        val passwordValidation = Validator.password(password)
        if (!passwordValidation.first) {
            binding.tilPassword.error = passwordValidation.second
            allValid = false
        }

        val locationValidation = Validator.generalString(location)
        if (!locationValidation.first) {
            binding.tilLocation.error = locationValidation.second
            allValid = false
        }

        val genderValidation = Validator.generalString(gender)
        if (!genderValidation.first) {
            binding.tilGender.error = genderValidation.second
            allValid = false
        }

        return allValid
    }

}