package com.neupanesushant.kastha.ui.fragment.main

import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.appcore.RouteConfig
import com.neupanesushant.kastha.appcore.RouteHelper
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.core.Router
import com.neupanesushant.kastha.databinding.FragmentProfileBinding
import com.neupanesushant.kastha.domain.model.User
import com.neupanesushant.kastha.extra.Preferences
import com.neupanesushant.kastha.ui.activity.AuthenticationActivity
import com.neupanesushant.kastha.ui.fragment.chat.ChatMessagingFragment
import com.neupanesushant.kastha.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_profile

    private val userViewModel: UserViewModel by sharedViewModel()
    override fun setupViews() {
        showLoading()
    }

    override fun setupEventListener() {
        binding.btnLogOut.setOnClickListener {
            showConfirmationDialog()
        }

        binding.btnAddProduct.setOnClickListener {
            RouteHelper.routeFullScreenContainerActivity(
                requireActivity(),
                RouteConfig.ADD_PRODUCT_FRAGMENT
            )
        }

        binding.btnChatWithCustomers.setOnClickListener {
            RouteHelper.routeFullScreenContainerActivity(
                requireActivity(),
                RouteConfig.CHAT_FRAGMENT
            )
        }

        binding.btnChatWithAdmin.setOnClickListener {
            val data = bundleOf(
                ChatMessagingFragment.CURRENT_USER_ID to Preferences.getUserId()
            )
            RouteHelper.routeFullScreenContainerActivity(
                requireActivity(),
                RouteConfig.CHAT_MESSAGING_FRAGMENT,
                data
            )
        }

        binding.btnEditProfile.setOnClickListener {
            RouteHelper.routeFullScreenContainerActivity(
                requireActivity(),
                RouteConfig.PROFILE_UPDATE_FRAGMENT
            )
        }
    }

    override fun setupObserver() {
        userViewModel.userDetail.observe(viewLifecycleOwner) {
            hideLoading()
            setupUserDetails(it)
            setupViewBasedOnRole(it)
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
                userViewModel.removeUser()
                Router(requireActivity()).routeClearTask(AuthenticationActivity::class.java)
            }
            .setNegativeButton("CANCEL") { dialog, value ->
                dialog.dismiss()
            }
            .show()
    }

    private fun setupViewBasedOnRole(user: User) {
        val roles = user.roles
        val isAdmin = roles.filter { it.name.equals("ADMIN") }.isNotEmpty()

        binding.btnChatWithAdmin.isVisible = !isAdmin
        binding.btnChatWithCustomers.isVisible = isAdmin

    }
}