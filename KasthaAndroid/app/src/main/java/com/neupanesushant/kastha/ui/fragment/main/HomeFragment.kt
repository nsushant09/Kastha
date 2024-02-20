package com.neupanesushant.kastha.ui.fragment.main

import android.annotation.SuppressLint
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.appcore.RouteConfig
import com.neupanesushant.kastha.appcore.RouteHelper
import com.neupanesushant.kastha.core.AppConfig
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.core.Router
import com.neupanesushant.kastha.databinding.FragmentHomeBinding
import com.neupanesushant.kastha.databinding.ItemHomeCarouselBinding
import com.neupanesushant.kastha.databinding.ItemMiniProductCardBinding
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.domain.usecase.managers.GlideManager
import com.neupanesushant.kastha.extra.extensions.dpToPx
import com.neupanesushant.kastha.ui.adapter.LargeProductCardAdapter
import com.neupanesushant.kastha.ui.adapter.ProductHorizontalCardAdapter
import com.neupanesushant.kastha.ui.adapter.RVAdapter
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_home

    private val products: List<Product>
            by inject(named("test_products"))
    private val carouselImages: List<String>
            by inject(named("test_carousel_images"))

    override fun setupViews() {

        setupCarouselView()
        setCarouselData(carouselImages)
        setupRecommendedProducts(products)
        setupLatestProducts(products)
        setupAllProducts(products)
    }

    override fun setupEventListener() {
        binding.btnFavourites.setOnClickListener {
            Router(requireActivity()).route(
                R.id.main_fragment_container,
                AppConfig.getFragment(RouteConfig.FAVOURITES_FRAGMENT)
            )
        }
    }

    override fun setupObserver() {
    }

    private fun setupCarouselView() {
        binding.carouselRecyclerView.apply {
            val snapHelper = CarouselSnapHelper()
            snapHelper.attachToRecyclerView(this)
            val carouselLayoutManager = CarouselLayoutManager()
            carouselLayoutManager.carouselAlignment = CarouselLayoutManager.ALIGNMENT_START
            layoutManager = carouselLayoutManager
        }
    }

    private fun setCarouselData(imageUrls: List<String>) {
        val adapter = RVAdapter<String, ItemHomeCarouselBinding>(
            R.layout.item_home_carousel,
            imageUrls
        ) { mBinding, data, _ ->
            GlideManager.load(requireContext(), data, mBinding.carouselImageView)
        }
        binding.carouselRecyclerView.adapter = adapter
    }

    private fun setupRecommendedProducts(products: List<Product>) {
        val snapHelper = LinearSnapHelper()
        binding.recommendedRvLayout.apply {
            val title = "Recommended For You"
            tvRecyclerViewTitle.text = title
            titledRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            snapHelper.attachToRecyclerView(titledRecyclerView)
            titledRecyclerView.adapter = getMiniProductCardAdapter(products)
        }
    }

    private fun setupLatestProducts(products: List<Product>) {
        val snapHelper = LinearSnapHelper()
        binding.latestRvLayout.apply {
            val title = "Latest Products"
            tvRecyclerViewTitle.text = title
            titledRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            snapHelper.attachToRecyclerView(titledRecyclerView)
            titledRecyclerView.adapter = getMiniProductCardAdapter(products)
        }
    }

    private fun setupAllProducts(products: List<Product>) {
        binding.allRvLayout.apply {
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

    @SuppressLint("SetTextI18n")
    private fun getMiniProductCardAdapter(products: List<Product>) =
        RVAdapter<Product, ItemMiniProductCardBinding>(
            R.layout.item_mini_product_card,
            products
        ) { mBinding, data, _ ->
            mBinding.tvProductTitle.text = data.name
            mBinding.tvProductPrice.text = "Rs." + data.price
            mBinding.cvArFeatured.isVisible = data.model != null
            mBinding.root.setOnClickListener {
                RouteHelper.routeProductDetail(requireActivity(), data)
            }
            if (data.images.isNotEmpty()) {
                GlideManager.load(
                    requireContext(),
                    data.images.shuffled()[0].url,
                    mBinding.ivProductImage
                )
            }
        }

    private fun setupSearchView() {
        binding.layoutSearchView.apply {
            searchView.editText.setOnEditorActionListener { textView, i, keyEvent ->
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    // TODO : Search for value and update rv
                    true
                }
                false
            }
        }
    }

    private fun setSearchViewContentVisibility(isProductsAvailable: Boolean) {
        binding.layoutSearchView.apply {
            rvSearchView.isVisible = isProductsAvailable
            llInvalidContainer.isVisible = !isProductsAvailable
        }
    }

    private fun setupSearchedProducts(products: List<Product>) {
        binding.layoutSearchView.apply {
            rvSearchView.layoutManager = LinearLayoutManager(requireContext())
            rvSearchView.adapter = ProductHorizontalCardAdapter(requireActivity(), products)
        }
    }
}