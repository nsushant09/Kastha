package com.neupanesushant.kastha.ui.fragment.main

import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentProfileBinding
import com.neupanesushant.kastha.domain.model.User
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_profile

    private val user: User by inject(named("test_user"))
    override fun setupViews() {
        setupUserDetails(user)
    }

    override fun setupEventListener() {
    }

    override fun setupObserver() {
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
}