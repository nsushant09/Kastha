package com.neupanesushant.kastha.ui.fragment.main

import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.core.Router
import com.neupanesushant.kastha.databinding.FragmentProfileBinding
import com.neupanesushant.kastha.domain.model.User
import com.neupanesushant.kastha.extra.Preferences
import com.neupanesushant.kastha.ui.activity.AuthenticationActivity
import com.neupanesushant.kastha.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_profile

    private val userViewModel: UserViewModel by viewModel()
    override fun setupViews() {
    }

    override fun setupEventListener() {
        binding.btnLogOut.setOnClickListener {
            showConfirmationDialog()
        }
    }

    override fun setupObserver() {
        userViewModel.userDetail.observe(viewLifecycleOwner) {
            setupUserDetails(it)
        }
    }

    private fun setupUserDetails(user: User) {
        binding.apply {
            val fullName = "${user.firstName} ${user.lastName}"
            tvUserName.text = fullName
            tvUserEmail.text = user.email
            tvUserLocation.text = user.location
            val profileAvatar =
                if (isUserMale(user.gender)) R.raw.lottie_male_profile else R.raw.lottie_female_profile
            lavUserProfileAvatar.setAnimation(profileAvatar)
        }
    }

    private fun isUserMale(gender: String): Boolean {
        return gender.first().equals('m', true)
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Sign Out")
            .setMessage("Are you sure you want to sign out?")
            .setPositiveButton("YES") { dialog, value ->
                Preferences.onLogOut()
                Router(requireActivity()).routeClearTask(AuthenticationActivity::class.java)
            }
            .setNegativeButton("CANCEL") { dialog, value ->
                dialog.dismiss()
            }
            .show()
    }
}