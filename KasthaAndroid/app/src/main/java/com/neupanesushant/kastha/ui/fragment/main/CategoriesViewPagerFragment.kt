package com.neupanesushant.kastha.ui.fragment.main

import android.os.Build
import android.util.Log
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentCategoriesViewPagerBinding
import com.neupanesushant.kastha.domain.model.Category
import com.neupanesushant.kastha.domain.model.Product
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class CategoriesViewPagerFragment : BaseFragment<FragmentCategoriesViewPagerBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_categories_view_pager

    private lateinit var category: Category
    private val products : List<Product> by inject(named("test_products"))
    override fun initialize() {
        category = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("category", Category::class.java) ?: return
        } else {
            arguments?.getParcelable<Category>("category") ?: return
        }
    }

    override fun setupViews() {
    }

    override fun setupEventListener() {
    }

    override fun setupObserver() {
    }
}