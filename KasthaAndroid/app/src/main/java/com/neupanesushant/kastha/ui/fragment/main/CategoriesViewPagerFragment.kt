package com.neupanesushant.kastha.ui.fragment.main

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentCategoriesViewPagerBinding
import com.neupanesushant.kastha.databinding.ItemProductHorizontalBinding
import com.neupanesushant.kastha.domain.model.Category
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.domain.usecase.managers.GlideManager
import com.neupanesushant.kastha.ui.adapter.RVAdapter
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
        binding.rvCategoryProducts.adapter = getProductsAdapter(products)
    }

    @SuppressLint("SetTextI18n")
    private fun getProductsAdapter(products: List<Product>) =
        RVAdapter<Product, ItemProductHorizontalBinding>(
            R.layout.item_product_horizontal,
            products
        ) { mBinding, data, datas ->
            mBinding.tvProductTitle.text = data.name
            mBinding.tvProductPrice.text = "Rs." + data.price
            mBinding.layoutArFeatured.cvArFeatured.isVisible = data.model != null
            mBinding.tvProductRating.text = "5.0"
            mBinding.root.setOnLongClickListener {
                mBinding.ivProductImage.foreground =
                    ContextCompat.getDrawable(requireContext(), R.drawable.overlay_selected_item)
                true
            }
            if (data.images.isNotEmpty()) {
                GlideManager.load(
                    requireContext(),
                    data.images.shuffled()[0].url,
                    mBinding.ivProductImage
                )
            }
        }
}