package com.neupanesushant.kastha.ui.fragment.main

import android.content.res.Resources
import android.os.Build
import androidx.recyclerview.widget.LinearLayoutManager
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentCategoriesViewPagerBinding
import com.neupanesushant.kastha.domain.model.Category
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.domain.managers.GlideManager
import com.neupanesushant.kastha.ui.adapter.ProductHorizontalCardAdapter
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class CategoriesViewPagerFragment : BaseFragment<FragmentCategoriesViewPagerBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_categories_view_pager

    private lateinit var category: Category
    private val products: List<Product> by inject(named("test_products"))
    override fun initialize() {
        category = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("category", Category::class.java) ?: return
        } else {
            arguments?.getParcelable<Category>("category") ?: return
        }
    }

    override fun setupViews() {
        binding.tvCategoryTitle.text = category.name
        setupCategoryImageView()
    }

    override fun setupEventListener() {
    }

    override fun setupObserver() {
        setupProducts(products)
    }

    private fun setupCategoryImageView() {
        val layoutParams = binding.ivCategoryImage.layoutParams
        val height = (Resources.getSystem().displayMetrics.widthPixels * 9) / 16
        layoutParams.height = height
        binding.ivCategoryImage.layoutParams = layoutParams
        GlideManager.load(requireContext(), category.imageUrl, binding.ivCategoryImage)
    }

    private fun setupProducts(products: List<Product>) {
        binding.rvCategoryProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCategoryProducts.adapter =
            ProductHorizontalCardAdapter(requireActivity(), products)
    }
}