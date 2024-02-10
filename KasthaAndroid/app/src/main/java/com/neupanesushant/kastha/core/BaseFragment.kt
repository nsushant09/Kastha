package com.neupanesushant.kastha.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.extra.Constants

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    protected lateinit var binding: T

    @get:LayoutRes
    protected abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            layoutId,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        animate()
        initialize()
        setupViews()
        setupEventListener()
        setupObserver()
        setupExtra()
    }

    abstract fun setupViews()
    abstract fun setupEventListener()
    abstract fun setupObserver()

    protected open fun setupExtra() {}
    protected open fun initialize() {}
    protected open fun animate() {
        AnimationUtils.loadAnimation(
            requireContext(),
             androidx.appcompat.R.anim.abc_fade_in
        ).also {
            binding.root.animation = it
        }
    }
}