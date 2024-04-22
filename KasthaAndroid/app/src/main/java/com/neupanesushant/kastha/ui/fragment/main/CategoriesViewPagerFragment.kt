package com.neupanesushant.kastha.ui.fragment.main

import android.content.res.Resources
import android.os.Build
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentCategoriesViewPagerBinding
import com.neupanesushant.kastha.domain.model.Category
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.ui.activity.MainActivity
import com.neupanesushant.kastha.ui.adapter.ProductHorizontalCardAdapter
import com.neupanesushant.kastha.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CategoriesViewPagerFragment : BaseFragment<FragmentCategoriesViewPagerBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_categories_view_pager

    private lateinit var category: Category
    private val productViewModel: ProductViewModel by sharedViewModel()
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
        binding.btnHomeFragment.setOnClickListener {
            (requireActivity() as MainActivity).setSelectedItem(R.id.menuBnvHome)
        }
    }

    override fun setupObserver() {
        productViewModel.allProduct.observe(viewLifecycleOwner) { products ->
            val categoryProducts = products.filter { it.category == category }
            setupProducts(categoryProducts)

            binding.llEmptyView.isVisible = categoryProducts.isEmpty()
            binding.rvCategoryProducts.isVisible = categoryProducts.isNotEmpty()
        }
    }

    private fun setupCategoryImageView() {
        val layoutParams = binding.ivCategoryImage.layoutParams
        val height = (Resources.getSystem().displayMetrics.widthPixels * 9) / 16
        layoutParams.height = height
        binding.ivCategoryImage.layoutParams = layoutParams

        Glide.with(requireContext())
            .load(category.imageUrl)
            .into(binding.ivCategoryImage)
    }

    private fun setupProducts(products: List<Product>) {
        binding.rvCategoryProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCategoryProducts.adapter =
            ProductHorizontalCardAdapter(requireActivity(), products)
    }
}