package com.neupanesushant.kastha.ui.fragment

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.GestureDetector
import android.view.animation.AnimationUtils
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentOnboardingBinding
import com.neupanesushant.kastha.extra.helper.DoubleTapListener
import com.neupanesushant.kastha.ui.dialog.LoadingDialog

class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_onboarding

    override fun setupViews() {
        animate()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setupEventListener() {
        val gestureDetector = GestureDetector(requireContext(), DoubleTapListener {
            val dialog = LoadingDialog()
            dialog.show(childFragmentManager, dialog::class.java.name)
            Handler(Looper.getMainLooper()).postDelayed({
                dialog.dismiss()
            }, 10000)
        })
        binding.root.setOnTouchListener { _, motionEvent ->
            gestureDetector.onTouchEvent(motionEvent)
            true
        }
    }

    override fun setupObserver() {
    }

    private fun animate() {
        binding.apply {
            AnimationUtils.loadAnimation(
                requireContext(), com.google.android.material.R.anim.abc_slide_in_bottom
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