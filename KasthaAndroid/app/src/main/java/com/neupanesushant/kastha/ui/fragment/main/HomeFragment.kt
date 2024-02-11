package com.neupanesushant.kastha.ui.fragment.main

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.bumptech.glide.Glide
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentHomeBinding
import com.neupanesushant.kastha.databinding.ItemHomeCarouselBinding
import com.neupanesushant.kastha.databinding.ItemMiniProductCardBinding
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.ui.adapter.RVAdapter
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_home

    private val products: List<Product> by inject(named("test_products"))
    private val carouselImages: List<String> by inject(named("test_carousel_images"))
    override fun setupViews() {
        setupCarouselView()
        setCarouselData(carouselImages)
        setupRecommendedProducts()
        setupLatestProducts()
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
            Glide.with(requireContext())
                .load(data)
                .into(mBinding.carouselImageView)
        }

        binding.carouselRecyclerView.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    private fun setupRecommendedProducts() {
        val snapHelper = LinearSnapHelper()
        binding.recommendedRvLayout.apply {
            tvRecyclerViewTitle.text = "Recommended For You"
            btnFullContent.isVisible = true
            titledRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            snapHelper.attachToRecyclerView(titledRecyclerView)
            titledRecyclerView.adapter = miniProductCardAdapter
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupLatestProducts() {
        val snapHelper = LinearSnapHelper()
        binding.latestRvLayout.apply {
            tvRecyclerViewTitle.text = "Latest Products"
            btnFullContent.isVisible = true
            titledRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            snapHelper.attachToRecyclerView(titledRecyclerView)
            titledRecyclerView.adapter = miniProductCardAdapter
        }
    }

    @SuppressLint("SetTextI18n")
    private val miniProductCardAdapter = RVAdapter<Product, ItemMiniProductCardBinding>(
        R.layout.item_mini_product_card,
        products
    ) { mBinding, data, datas ->
        mBinding.tvProductTitle.text = data.name
        mBinding.tvProductPrice.text = "$" + data.price.toString()
        mBinding.cvArFeatured.isVisible = data.model != null

        if (data.images.isNotEmpty()) {
            Glide.with(requireContext())
                .load(data.images[0])
                .into(mBinding.ivProductImage)
        }
    }

}