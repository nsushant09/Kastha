package com.neupanesushant.kastha.ui.fragment.admin

import android.annotation.SuppressLint
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.appcore.RouteConfig
import com.neupanesushant.kastha.core.AppConfig
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.core.Router
import com.neupanesushant.kastha.databinding.FragmentProductEditBinding
import com.neupanesushant.kastha.databinding.ItemProductHorizontalBinding
import com.neupanesushant.kastha.domain.managers.GlideManager
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.ui.activity.MainActivity
import com.neupanesushant.kastha.ui.adapter.RVAdapter
import com.neupanesushant.kastha.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductEditFragment : BaseFragment<FragmentProductEditBinding>() {

    private val productsViewModel: ProductViewModel by viewModel()
    override val layoutId: Int
        get() = R.layout.fragment_product_edit

    override fun setupViews() {
        showLoading()
    }

    override fun setupEventListener() {
        binding.btnAddProductFragment.setOnClickListener {
            (requireActivity() as MainActivity).setSelectedItem(R.id.menuBnvHome)
        }
    }

    override fun setupObserver() {
        productsViewModel.allProduct.observe(viewLifecycleOwner) { products ->
            hideLoading()
            binding.llEmptyView.isVisible = products.isEmpty()
            binding.rvProducts.isVisible = products.isNotEmpty()
            setupFavouriteProducts(products)
        }
    }

    private fun setupFavouriteProducts(products: List<Product>) {
        binding.rvProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProducts.adapter = getProductsAdapter(products)
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

            mBinding.root.setOnClickListener { onFavouriteProductClick(mBinding, data) }

            if (data.images.isNotEmpty()) {
                GlideManager.load(
                    requireContext(),
                    data.images[0].url,
                    mBinding.ivProductImage
                )
            }
        }

    private fun onFavouriteProductClick(binding: ItemProductHorizontalBinding, product: Product) {
        val data = bundleOf(
            UpdateProductFragment.PRODUCT_ARGUMENT to product
        )

        Router(requireActivity(), data).route(
            R.id.fullscreen_fragment_container,
            AppConfig.getFragment(RouteConfig.UPDATE_PRODUCT_FRAGMENT)
        )
    }

}