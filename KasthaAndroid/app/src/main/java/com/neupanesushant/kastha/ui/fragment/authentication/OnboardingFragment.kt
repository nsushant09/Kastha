package com.neupanesushant.kastha.ui.fragment.authentication

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.GestureDetector
import android.view.animation.AnimationUtils
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.appcore.RouteConfig
import com.neupanesushant.kastha.core.AppConfig
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.core.Router
import com.neupanesushant.kastha.databinding.FragmentOnboardingBinding
import com.neupanesushant.kastha.extra.helper.DoubleTapListener
import com.neupanesushant.kastha.ui.activity.MainActivity

class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_onboarding

    override fun setupViews() {
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setupEventListener() {

        binding.btnSignIn.setOnClickListener {
            Router(requireActivity()).route(
                R.id.authentication_fragment_container,
                AppConfig.getFragment(RouteConfig.LOGIN_FRAGMENT)
            )
        }

        binding.btnCreateAccount.setOnClickListener {
            Router(requireActivity()).route(
                R.id.authentication_fragment_container,
                AppConfig.getFragment(RouteConfig.SIGN_UP_FRAGMENT)
            )
        }

        val gestureDetector = GestureDetector(requireContext(), DoubleTapListener {
            Router(requireActivity()).routeClearTask(MainActivity::class.java)
            Handler(Looper.getMainLooper()).postDelayed({
//                dialog.dismiss()
            }, 10000)
        })
        binding.root.setOnTouchListener { _, motionEvent ->
            gestureDetector.onTouchEvent(motionEvent)
            true
        }
    }

    override fun setupObserver() {
    }

    override fun animate() {
        binding.apply {
            AnimationUtils.loadAnimation(
                requireContext(), androidx.appcompat.R.anim.abc_slide_in_bottom
            ).also {
                icOnboardingUser.animation = it
                btnCreateAccount.animation = it
                btnSignIn.animation = it
            }

            tvAppName.animation =
                AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_left)
            tvSlogan.animation =
                AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_right)
        }
    }
}