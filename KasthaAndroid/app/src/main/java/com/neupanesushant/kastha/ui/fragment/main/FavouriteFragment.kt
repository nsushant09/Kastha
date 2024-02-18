package com.neupanesushant.kastha.ui.fragment.main

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentFavouriteBinding
import com.neupanesushant.kastha.databinding.ItemProductHorizontalBinding
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.domain.usecase.managers.GlideManager
import com.neupanesushant.kastha.ui.adapter.RVAdapter
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class FavouriteFragment : BaseFragment<FragmentFavouriteBinding>() {

    private val products: List<Product>
            by inject(named("test_products"))

    private val selectionItemsIdSet = mutableSetOf<Int>()
    private var isSelectionEnabled: Boolean = false
        set(value) {
            field = value
            if (value) onSelectionEnabled() else onSelectionDisabled()
        }

    override val layoutId: Int
        get() = R.layout.fragment_favourite

    override fun setupViews() {
        setupFavouriteProducts(products)
    }

    override fun setupEventListener() {
        binding.btnCancelSelection.setOnClickListener {
            isSelectionEnabled = false
        }
        binding.btnDeleteSelection.setOnClickListener {
            // TODO : Delete Selection
        }
    }

    override fun setupObserver() {
    }

    private fun setupFavouriteProducts(products: List<Product>) {
        binding.rvFavouriteProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavouriteProducts.adapter = getCartProductsAdapter(products)
    }

    @SuppressLint("SetTextI18n")
    private fun getCartProductsAdapter(products: List<Product>) =
        RVAdapter<Product, ItemProductHorizontalBinding>(
            R.layout.item_product_horizontal,
            products
        ) { mBinding, data, datas ->
            mBinding.tvProductTitle.text = data.name
            mBinding.tvProductPrice.text = "Rs." + data.price
            mBinding.layoutArFeatured.cvArFeatured.isVisible = data.model != null
            mBinding.tvProductRating.text = "5.0"

            mBinding.root.setOnClickListener { onCartProductClick(mBinding, data) }
            mBinding.root.setOnLongClickListener {
                onCardProductLongClick(mBinding, data)
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

    private fun onCardProductLongClick(binding: ItemProductHorizontalBinding, product: Product) {
        if (!isSelectionEnabled) {
            isSelectionEnabled = true
            selectionItemsIdSet.add(product.id)
            binding.ivProductImage.foreground =
                ContextCompat.getDrawable(requireContext(), R.drawable.overlay_selected_item)
        }
    }

    private fun onCartProductClick(binding: ItemProductHorizontalBinding, product: Product) {
        if (isSelectionEnabled) {
            if (selectionItemsIdSet.contains(product.id)) {
                selectionItemsIdSet.remove(product.id)
                binding.ivProductImage.foreground = null
                if (selectionItemsIdSet.isEmpty()) isSelectionEnabled = false
            } else {
                selectionItemsIdSet.add(product.id)
                binding.ivProductImage.foreground =
                    ContextCompat.getDrawable(requireContext(), R.drawable.overlay_selected_item)
            }
            return;
        }
        // TODO : Navigate to Product Detail
    }

    private fun onSelectionEnabled() {
        binding.llDeletionContainer.isVisible = true
    }

    private fun onSelectionDisabled() {
        binding.llDeletionContainer.isVisible = false
        selectionItemsIdSet.clear()
        binding.rvFavouriteProducts.adapter?.notifyItemRangeChanged(
            0,
            binding.rvFavouriteProducts.adapter?.itemCount ?: 0
        )
    }

    private fun deleteSelection() {

    }

}