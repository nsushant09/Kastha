package com.neupanesushant.kastha.ui.fragment.main

import android.annotation.SuppressLint
import com.google.android.material.tabs.TabLayoutMediator
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentCategoriesBinding
import com.neupanesushant.kastha.domain.model.Category
import com.neupanesushant.kastha.domain.managers.GlideManager
import com.neupanesushant.kastha.ui.adapter.CategoryViewPagerAdapter
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_categories

    private val categories: List<Category> by inject(named("test_categories"))

    override fun setupViews() {
    }

    override fun setupEventListener() {
    }

    override fun setupObserver() {
        setupViewPager(categories)
        setupTabLayout(categories)
    }

    @SuppressLint("CheckResult")
    private fun setupTabLayout(categories: List<Category>) {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = categories[position].name
        }.attach()
    }

    private fun setupViewPager(categories: List<Category>) {
        binding.viewPager.adapter =
            CategoryViewPagerAdapter(childFragmentManager, lifecycle, categories)
    }
}