package com.neupanesushant.kastha.ui.fragment.main

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentHomeBinding
import com.neupanesushant.kastha.databinding.ItemHomeCarouselBinding
import com.neupanesushant.kastha.databinding.ItemLargeProductCardBinding
import com.neupanesushant.kastha.databinding.ItemMiniProductCardBinding
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.domain.usecase.managers.GlideManager
import com.neupanesushant.kastha.domain.usecase.managers.PaletteManager
import com.neupanesushant.kastha.extra.extensions.dpToPx
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
        setupRecommendedProducts()
        setupLatestProducts()
        setupAllProducts()
    }

    override fun setupEventListener() {
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

    private fun setupRecommendedProducts() {
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

    private fun setupLatestProducts() {
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

    private fun setupAllProducts() {
        binding.allRvLayout.apply {
            val params = titledRecyclerView.layoutParams as ConstraintLayout.LayoutParams
            params.setMargins(dpToPx(requireContext(), 16f).toInt(), 0, 0, 0)
            titledRecyclerView.layoutParams = params

            val title = "All Products"
            tvRecyclerViewTitle.text = title
            titledRecyclerView.layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            titledRecyclerView.adapter = getLargeProductCardAdapter(products)
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

            if (data.images.isNotEmpty()) {
                GlideManager.load(
                    requireContext(),
                    data.images.shuffled()[0].url,
                    mBinding.ivProductImage
                )
            }
        }

    @SuppressLint("SetTextI18n")
    private fun getLargeProductCardAdapter(products: List<Product>) =
        RVAdapter<Product, ItemLargeProductCardBinding>(
            R.layout.item_large_product_card,
            products
        ) { mBinding, data, _ ->

            mBinding.root.layoutParams.width =
                ((Resources.getSystem().displayMetrics.widthPixels / 2) - dpToPx(
                    requireContext(),
                    24f
                )).toInt()

            mBinding.cvProductImage.layoutParams.height = (mBinding.root.layoutParams.width * 4) / 3

            mBinding.tvProductTitle.text = data.name
            mBinding.tvProductPrice.text = "Rs." + data.price
            mBinding.layoutArFeatured.cvArFeatured.isVisible = data.model != null

            if (data.images.isNotEmpty()) {
                GlideManager.loadWithBitmap(
                    requireContext(),
                    data.images.shuffled()[0].url
                ) { bitmap, _ ->
                    PaletteManager.setBackgroundDynamically(
                        requireContext(),
                        mBinding.cvProductImage,
                        bitmap
                    )
                    mBinding.ivProductImage.setImageBitmap(bitmap)
                }
            }
        }

}