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
import com.neupanesushant.kastha.domain.managers.GlideManager
import com.neupanesushant.kastha.domain.model.CartProduct
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.extra.extensions.dpToPx
import com.neupanesushant.kastha.extra.extensions.itemSize
import com.neupanesushant.kastha.ui.activity.MainActivity
import com.neupanesushant.kastha.ui.adapter.LargeProductCardAdapter
import com.neupanesushant.kastha.ui.adapter.RVAdapter
import com.neupanesushant.kastha.ui.dialog.DialogUtils
import com.neupanesushant.kastha.viewmodel.CartViewModel
import com.neupanesushant.kastha.viewmodel.FavouriteViewModel
import com.neupanesushant.kastha.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : BaseFragment<FragmentCartBinding>() {

    private val productViewModel: ProductViewModel by viewModel()
    private val cartViewModel: CartViewModel by viewModel()
    private val favouriteViewModel: FavouriteViewModel by viewModel()

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
            deleteSelection()
        }
        binding.btnHomeFragment.setOnClickListener {
            (requireActivity() as MainActivity).setSelectedItem(R.id.menuBnvHome)
        }
    }

    override fun setupObserver() {
        cartViewModel.allProducts.observe(viewLifecycleOwner) { cartProducts ->
            binding.llEmptyView.isVisible = cartProducts.isEmpty()
            binding.rvCartProducts.isVisible = cartProducts.isNotEmpty()

            setupCartProducts(cartProducts)
            setTotalAmount(cartProducts)
        }

        productViewModel.allProduct.observe(viewLifecycleOwner) {
            setupForYouProducts(it.sortedByDescending { it.id }.slice(0..9))
        }
    }

    private fun setupCartProducts(products: List<CartProduct>) {
        binding.rvCartProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCartProducts.adapter = getCartProductsAdapter(products)
    }

    @SuppressLint("SetTextI18n")
    private fun getCartProductsAdapter(products: List<CartProduct>) =
        RVAdapter<CartProduct, ItemProductHorizontalBinding>(
            R.layout.item_product_horizontal,
            products
        ) { mBinding, data, _ ->
            val product = data.product
            mBinding.tvProductTitle.text = product.name
            mBinding.tvProductPrice.text = "Rs." + product.price
            mBinding.layoutArFeatured.cvArFeatured.isVisible = product.model != null
            mBinding.tvProductRating.text = "5.0"
            mBinding.layoutItemCount.tvItemCount.text = data.quantity.toString()
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

            if (product.images.isNotEmpty()) {
                GlideManager.load(
                    requireContext(),
                    product.images[0].url,
                    mBinding.ivProductImage
                )
            }
        }

    private fun onItemIncrement(view: TextView, product: CartProduct, prevCount: Int) {
        if (prevCount == product.product.stockQuantity) {
            toast("Maximum product quantity")
            return
        }
        val newCount = prevCount + 1
        view.text = newCount.toString()
        cartViewModel.increment(product.id) {
            DialogUtils.generalDialog(
                requireContext(),
                "An error occurred while adding the product to the cart. Please try again later.",
                "Error Adding"
            )
        }
    }

    private fun onItemDecrement(view: TextView, product: CartProduct, prevCount: Int) {
        if (prevCount == 1) {
            toast("Minimum product in cart")
            return
        }
        val newCount = prevCount - 1
        view.text = newCount.toString()
        cartViewModel.decrement(product.id) {
            DialogUtils.generalDialog(
                requireContext(),
                "An error occurred while removing products from cart. Please try again later.",
                "Error Removing from cart"
            )
        }
    }

    private fun onCardProductLongClick(
        binding: ItemProductHorizontalBinding,
        product: CartProduct
    ) {
        if (!isSelectionEnabled) {
            isSelectionEnabled = true
            selectionItemsIdSet.add(product.id)
            binding.ivProductImage.foreground =
                ContextCompat.getDrawable(requireContext(), R.drawable.overlay_selected_item)
        }
    }

    private fun onCartProductClick(binding: ItemProductHorizontalBinding, product: CartProduct) {
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
            return
        }
        RouteHelper.routeProductDetail(requireActivity(), product.product)
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
            titledRecyclerView.adapter = LargeProductCardAdapter(
                requireActivity(),
                products,
                itemSize(requireContext(), 0.5f, 24)
            )
        }
    }

    private fun onSelectionEnabled() {
        binding.llDeletionContainer.isVisible = true
    }

    private fun setTotalAmount(cartProducts: List<CartProduct>) {
        var total = 0.0
        cartProducts.forEach {
            total += it.quantity * it.product.price
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

    private fun deleteSelection() {
        cartViewModel.removeProducts(selectionItemsIdSet) {
            DialogUtils.generalDialog(
                requireContext(),
                "An error occurred while removing products from cart. Please try again later.",
                "Error Removing from cart"
            )
        }
        isSelectionEnabled = false
    }
}