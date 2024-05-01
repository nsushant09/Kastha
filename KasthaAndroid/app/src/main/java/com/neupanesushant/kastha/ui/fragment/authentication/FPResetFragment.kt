package com.neupanesushant.kastha.ui.fragment.authentication

import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.appcore.RouteConfig
import com.neupanesushant.kastha.core.AppConfig
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.core.Router
import com.neupanesushant.kastha.databinding.FragmentFpResetBinding
import com.neupanesushant.kastha.extra.Utils.getParcelable
import com.neupanesushant.kastha.extra.helper.Validator
import com.neupanesushant.kastha.ui.dialog.DialogUtils
import com.neupanesushant.kastha.viewmodel.AuthenticationViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FPResetFragment : BaseFragment<FragmentFpResetBinding>() {

    private lateinit var email: String

    private val authenticationViewModel by sharedViewModel<AuthenticationViewModel>()

    companion object {
        const val EMAIL_ARGUMENT = "EMAIL_ARGUMENT"
    }

    override val layoutId: Int
        get() = R.layout.fragment_fp_reset

    override fun initialize() {
        val emailData = getParcelable<String>(FPResetFragment.EMAIL_ARGUMENT)
        if (emailData == null) requireActivity().onBackPressedDispatcher.onBackPressed()
        email = emailData!!
    }

    override fun setupViews() {

    }

    override fun setupEventListener() {
        binding.btnBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        binding.btnConfirm.setOnClickListener {
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (!areAllDetailsValid(password, confirmPassword)) {
                return@setOnClickListener
            }

            authenticationViewModel.resetPassword(email, password, onFailure = {
                DialogUtils.generalDialog(requireContext(), it, "Error Resetting Password")
            }, onSuccess = {
                toast("Your password has been changed")
                Router(requireActivity()).routeClearTask(
                    AppConfig.getActivity(RouteConfig.AUTHENTICATION_ACTIVITY)
                )
            })
        }
    }

    private fun areAllDetailsValid(password: String, confirmPassword: String): Boolean {
        binding.apply {
            tilPassword.error = null
            tilConfirmPassword.error = null
        }
        var allValid = true
        val passwordValidation = Validator.password(password)
        if (!passwordValidation.first) {
            binding.tilPassword.error = passwordValidation.second
            allValid = false
        }

        if (!password.equals(confirmPassword, false)) {
            binding.tilConfirmPassword.error =
                "Confirmation password should match with the password above"
            allValid = false
        }
        return allValid
    }

    override fun setupObserver() {
    }
}