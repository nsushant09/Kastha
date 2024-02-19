package com.neupanesushant.kastha.ui.fragment.main

import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentProductDetailBinding
import com.neupanesushant.kastha.domain.model.Product

class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>() {

    companion object {
        const val PRODUCT_ARGUMENT = "PRODUCT_ARGUMENT"
    }

    override val layoutId: Int
        get() = R.layout.fragment_product_detail

    private lateinit var product: Product

    override fun initialize() {
        try {
            product = arguments?.getParcelable<Product>(PRODUCT_ARGUMENT)!!
        } catch (_: Exception) {
            requireActivity().finish()
        }
    }

    override fun setupViews() {
        binding.producttv.text = product.toString()
    }

    override fun setupEventListener() {

    }

    override fun setupObserver() {

    }
}