package com.neupanesushant.kastha.ui.fragment.admin

import android.widget.ArrayAdapter
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentProfileUpdateBinding
import com.neupanesushant.kastha.domain.model.User
import com.neupanesushant.kastha.domain.model.dto.UserUpdateDTO
import com.neupanesushant.kastha.extra.helper.Validator
import com.neupanesushant.kastha.viewmodel.UserViewModel
import org.koin.android.ext.android.inject

class ProfileUpdateFragment : BaseFragment<FragmentProfileUpdateBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_profile_update

    private val userViewModel: UserViewModel by inject()
    private var selectedUser: User? = null

    override fun setupViews() {
        setupGenderAutoCompleteView()
    }

    override fun setupEventListener() {
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.btnUpdateProfile.setOnClickListener {
            updateProfile()
        }
    }

    override fun setupObserver() {
        userViewModel.userDetail.observe(viewLifecycleOwner) {
            selectedUser = it
            setupInitialUserData(it)
        }
    }

    private fun setupInitialUserData(user: User) {
        binding.apply {
            etFirstName.setText(user.firstName)
            etLastName.setText(user.lastName)
            etEmail.setText(user.email)
            etLocation.setText(user.location)
            etGender.setText(user.gender, false)
        }
    }

    private fun setupGenderAutoCompleteView() {
        val options = arrayOf("Male", "Female")
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, options)
        binding.etGender.setAdapter(adapter)
    }

    private fun updateProfile() {

        if (selectedUser == null) {
            toast("User detail is not loaded to update")
            return
        }
        binding.apply {
            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val password = etNewPassword.text.toString()
            val location = etLocation.text.toString()
            val gender = etGender.text.toString()
            val email = etEmail.text.toString()
            val confirmPassword = etRePassword.text.toString()
            val id = selectedUser!!.id
            val roles = selectedUser!!.roles

            if (!areValidDetails(
                    firstName,
                    lastName,
                    email,
                    location,
                    gender,
                    password,
                    confirmPassword
                )
            ) {
                return
            }

            showLoading()
            userViewModel.updateUser(
                UserUpdateDTO(
                    id,
                    firstName,
                    lastName,
                    email,
                    confirmPassword,
                    location,
                    gender,
                    roles
                ),
                onFailure = {
                    hideLoading()
                    toast("Could not update user profile")
                },
                onSuccess = {
                    hideLoading()
                    toast("User profile updated")
                }
            )

        }
    }

    private fun areValidDetails(
        firstName: String,
        lastName: String,
        email: String,
        location: String,
        gender: String,
        password: String? = null,
        confirmPassword: String? = null,

        ): Boolean {
        // Clear any previous errors
        binding.apply {
            tilFirstName.error = null
            tilLastName.error = null
            tilEmail.error = null
            tilRePassword.error = null
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

        if (!password.isNullOrEmpty()) {
            val passwordValidation = Validator.password(password)
            if (!passwordValidation.first) {
                binding.tilNewPassword.error = passwordValidation.second
                allValid = false
            }

            if (!password.equals(confirmPassword, false)) {
                binding.tilRePassword.error =
                    "Confirmation password should match with the password above"
                allValid = false
            }
        }

        return allValid
    }
}