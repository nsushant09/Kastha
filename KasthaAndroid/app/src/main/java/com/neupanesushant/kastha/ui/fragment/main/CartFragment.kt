package com.neupanesushant.kastha.ui.fragment.main

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.appcore.RouteHelper
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentCartBinding
import com.neupanesushant.kastha.databinding.ItemProductHorizontalBinding
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.domain.usecase.managers.GlideManager
import com.neupanesushant.kastha.extra.extensions.dpToPx
import com.neupanesushant.kastha.ui.adapter.LargeProductCardAdapter
import com.neupanesushant.kastha.ui.adapter.RVAdapter
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class CartFragment : BaseFragment<FragmentCartBinding>() {

    private val products: List<Product>
            by inject(named("test_products"))

    private val selectionItemsIdSet = mutableSetOf<Int>()
    private var isSelectionEnabled: Boolean = false
        set(value) {
            field = value
            if (value) onSelectionEnabled() else onSelectionDisabled()
        }

    override val layoutId: Int
        get() = R.layout.fragment_cart

    override fun setupViews() {
    }

    override fun setupEventListener() {
        binding.btnCancelSelection.setOnClickListener {
            isSelectionEnabled = false
        }
        binding.btnDeleteSelection.setOnClickListener {
            // TODO : Delete Selection Data
        }
    }

    override fun setupObserver() {
        setupCartProducts(products)
        setupForYouProducts(products)
        setTotalAmount(products)
    }

    private fun setupCartProducts(products: List<Product>) {
        binding.rvCartProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCartProducts.adapter = getCartProductsAdapter(products)
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
            mBinding.layoutItemCount.tvItemCount.text = "1"
            mBinding.layoutItemCount.root.isVisible = true

            mBinding.root.setOnClickListener { onCartProductClick(mBinding, data) }
            mBinding.root.setOnLongClickListener {
                onCardProductLongClick(mBinding, data)
                true
            }

            mBinding.layoutItemCount.btnDecrement.setOnClickListener {
                mBinding.layoutItemCount.tvItemCount.apply {
                    onItemDecrement(this, data, this.text.toString().toInt())
                }
            }

            mBinding.layoutItemCount.btnIncrement.setOnClickListener {
                mBinding.layoutItemCount.tvItemCount.apply {
                    onItemIncrement(this, data, this.text.toString().toInt())
                }
            }

            if (data.images.isNotEmpty()) {
                GlideManager.load(
                    requireContext(),
                    data.images.shuffled()[0].url,
                    mBinding.ivProductImage
                )
            }
        }

    private fun onItemIncrement(view: TextView, product: Product, prevCount: Int) {
        // TODO : If prevCount == stockQuantity ; cannot increment
        val newCount = prevCount + 1
        view.text = newCount.toString()
        // TODO : Increment Count in Database
    }

    private fun onItemDecrement(view: TextView, product: Product, prevCount: Int) {
        if (prevCount == 1) return;
        val newCount = prevCount - 1
        view.text = newCount.toString()
        // TODO : Decrement Count in Database
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
        RouteHelper.routeProductDetail(requireActivity(), product)
    }

    private fun setupForYouProducts(products: List<Product>) {
        binding.rvForYou.apply {
            val params = titledRecyclerView.layoutParams as ConstraintLayout.LayoutParams
            params.setMargins(dpToPx(requireContext(), 16f).toInt(), 0, 0, 0)
            titledRecyclerView.layoutParams = params

            val title = "All Products"
            tvRecyclerViewTitle.text = title
            titledRecyclerView.layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            titledRecyclerView.adapter = LargeProductCardAdapter(requireActivity(), products)
        }
    }

    private fun onSelectionEnabled() {
        binding.llDeletionContainer.isVisible = true
    }

    private fun setTotalAmount(products: List<Product>) {
        var total: Double = 0.0
        products.forEach {
            total += it.price
            // TODO : multiply with count in cart
        }
        binding.tvTotalAmount.text = String.format("%.2f", total)
    }

    private fun onSelectionDisabled() {
        binding.llDeletionContainer.isVisible = false
        selectionItemsIdSet.clear()
        binding.rvCartProducts.adapter?.notifyItemRangeChanged(
            0,
            binding.rvCartProducts.adapter?.itemCount ?: 0
        )
    }

}