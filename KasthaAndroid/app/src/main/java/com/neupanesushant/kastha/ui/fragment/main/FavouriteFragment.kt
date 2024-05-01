package com.neupanesushant.kastha.ui.fragment.main

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.appcore.RouteHelper
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentFavouriteBinding
import com.neupanesushant.kastha.databinding.ItemProductHorizontalBinding
import com.neupanesushant.kastha.domain.managers.GlideManager
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.ui.activity.MainActivity
import com.neupanesushant.kastha.ui.adapter.RVAdapter
import com.neupanesushant.kastha.ui.dialog.DialogUtils
import com.neupanesushant.kastha.viewmodel.FavouriteViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FavouriteFragment : BaseFragment<FragmentFavouriteBinding>() {

    private val selectionItemsIdSet = mutableSetOf<Int>()
    private var isSelectionEnabled: Boolean = false
        set(value) {
            field = value
            if (value) onSelectionEnabled() else onSelectionDisabled()
        }

    private val favouriteViewModel: FavouriteViewModel by sharedViewModel()

    override val layoutId: Int
        get() = R.layout.fragment_favourite

    override fun setupViews() {
        showLoading()
        favouriteViewModel.getAllFavouriteProducts()
    }

    override fun setupEventListener() {
        binding.btnCancelSelection.setOnClickListener {
            isSelectionEnabled = false
        }
        binding.btnDeleteSelection.setOnClickListener {
            deleteSelection()
        }
        binding.btnHomeFragment.setOnClickListener {
            (requireActivity() as MainActivity).setSelectedItem(R.id.menuBnvHome)
        }
    }

    override fun setupObserver() {
        favouriteViewModel.favouriteProducts.observe(viewLifecycleOwner) { favoriteProducts ->
            hideLoading()
            binding.llEmptyView.isVisible = favoriteProducts.isEmpty()
            binding.rvFavouriteProducts.isVisible = favoriteProducts.isNotEmpty()

            setupFavouriteProducts(favoriteProducts)
        }
    }

    private fun setupFavouriteProducts(products: List<Product>) {
        binding.rvFavouriteProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavouriteProducts.adapter = getFavouriteProductsAdapter(products)
    }

    @SuppressLint("SetTextI18n")
    private fun getFavouriteProductsAdapter(products: List<Product>) =
        RVAdapter<Product, ItemProductHorizontalBinding>(
            R.layout.item_product_horizontal,
            products
        ) { mBinding, data, datas ->
            mBinding.tvProductTitle.text = data.name
            mBinding.tvProductPrice.text = "Rs." + data.price
            mBinding.layoutArFeatured.cvArFeatured.isVisible = data.model != null
            mBinding.tvProductRating.text = "5.0"

            mBinding.root.setOnClickListener { onFavouriteProductClick(mBinding, data) }
            mBinding.root.setOnLongClickListener {
                onFavouriteProductLongClick(mBinding, data)
                true
            }

            if (data.images.isNotEmpty()) {
                GlideManager.load(
                    requireContext(),
                    data.images[0].url,
                    mBinding.ivProductImage
                )
            }
        }

    private fun onFavouriteProductLongClick(
        binding: ItemProductHorizontalBinding,
        product: Product
    ) {
        if (!isSelectionEnabled) {
            isSelectionEnabled = true
            selectionItemsIdSet.add(product.id)
            binding.ivProductImage.foreground =
                ContextCompat.getDrawable(requireContext(), R.drawable.overlay_selected_item)
        }
    }

    private fun onFavouriteProductClick(binding: ItemProductHorizontalBinding, product: Product) {
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
        RouteHelper.routeProductDetail(requireActivity(), product)
    }

    private fun onSelectionEnabled() {
        binding.llDeletionContainer.isVisible = true
    }

    private fun onSelectionDisabled() {
        binding.llDeletionContainer.isVisible = false
        selectionItemsIdSet.clear()
    }

    private fun deleteSelection() {
        showLoading()
        favouriteViewModel.removeFromFavourite(selectionItemsIdSet, onFailure = {
            hideLoading()
            DialogUtils.generalDialog(
                requireContext(),
                "An error occurred while removing the product to your favourites. Please try again later.",
                "Error Removing from Favourites"
            )
        }, onSuccess = { hideLoading() })
        isSelectionEnabled = false
    }

}