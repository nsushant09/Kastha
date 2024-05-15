package com.neupanesushant.kastha.ui.fragment.main

import android.annotation.SuppressLint
import com.google.android.material.tabs.TabLayoutMediator
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentCategoriesBinding
import com.neupanesushant.kastha.domain.model.Category
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.ui.adapter.CategoryViewPagerAdapter
import com.neupanesushant.kastha.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_categories

    //    private val categoryViewModel: CategoryViewModel by sharedViewModel()
    private val productViewModel: ProductViewModel by sharedViewModel()

    override fun setupViews() {
        showLoading()
    }

    override fun setupEventListener() {
    }

    override fun setupObserver() {
        productViewModel.allProduct.observe(viewLifecycleOwner) { products ->
            hideLoading()
            val categories = products.map { it.category }.distinct()
            setupViewPager(categories)
            setupTabLayout(categories)
        }
//        categoryViewModel.categories.observe(viewLifecycleOwner) {
//            hideLoading()
//            setupViewPager(it)
//            setupTabLayout(it)
//        }
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