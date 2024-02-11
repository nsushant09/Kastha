package com.neupanesushant.kastha.ui.fragment.main

import com.bumptech.glide.Glide
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentHomeBinding
import com.neupanesushant.kastha.databinding.ItemHomeCarouselBinding
import com.neupanesushant.kastha.ui.adapter.RVAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_home

    override fun setupViews() {
        setupCarouselView()
        setupSearchView()
        setCarouselData(getImages())
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

    private fun setupSearchView() {
    }

    private fun getImages() = listOf<String>(
        "https://images.pexels.com/photos/15927820/pexels-photo-15927820/free-photo-of-cards-and-decorations-for-the-lunar-new-year.jpeg?auto=compress&cs=tinysrgb&w=800&lazy=load",
        "https://images.pexels.com/photos/19040174/pexels-photo-19040174/free-photo-of-a-cup-of-tea-and-flowers-on-the-table.jpeg?auto=compress&cs=tinysrgb&w=800&lazy=load",
        "https://images.pexels.com/photos/6747320/pexels-photo-6747320.jpeg?auto=compress&cs=tinysrgb&w=800&lazy=load",
        "https://images.pexels.com/photos/6908252/pexels-photo-6908252.jpeg?auto=compress&cs=tinysrgb&w=800&lazy=load",
        "https://images.pexels.com/photos/20035207/pexels-photo-20035207/free-photo-of-red-house-by-the-sea.jpeg?auto=compress&cs=tinysrgb&w=800&lazy=load",
        "https://images.pexels.com/photos/3687633/pexels-photo-3687633.jpeg?auto=compress&cs=tinysrgb&w=800&lazy=load"
    )
}