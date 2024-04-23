package com.neupanesushant.kastha.ui.activity

import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseActivity
import com.neupanesushant.kastha.databinding.ActivityAugmentedViewBinding
import com.neupanesushant.kastha.ui.fragment.main.AugmentedViewFragment

class AugmentedViewActivity : BaseActivity<ActivityAugmentedViewBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_augmented_view

    override fun setupViews() {
        val data = intent.extras?.getBundle("data")
        if (data == null) finish()
        val fragment = AugmentedViewFragment()
        fragment.arguments = data
        supportFragmentManager.beginTransaction()
            .replace(R.id.augmented_view_fragment_container, fragment)
            .disallowAddToBackStack().commit()
    }

    override fun setupEventListener() {
    }

    override fun setupObserver() {
    }

    fun binding() = binding
}