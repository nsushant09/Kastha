package com.neupanesushant.kastha.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.neupanesushant.kastha.appcore.RouteHelper
import com.neupanesushant.kastha.databinding.ItemProductHorizontalBinding
import com.neupanesushant.kastha.domain.managers.GlideManager
import com.neupanesushant.kastha.domain.model.Product

class ProductHorizontalCardAdapter(
    private val activity: Activity,
    private val products: List<Product>
) :
    RecyclerView.Adapter<ProductHorizontalCardAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemProductHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProductHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mBinding = holder.binding
        val data = products[position]
        mBinding.tvProductTitle.text = data.name
        mBinding.tvProductPrice.text = "Rs." + data.price
        mBinding.layoutArFeatured.cvArFeatured.isVisible = data.model != null
        mBinding.tvProductRating.text = "5.0"
        mBinding.root.setOnClickListener {
            RouteHelper.routeProductDetail(activity, data)
        }
        if (data.images.isNotEmpty()) {
            GlideManager.load(
                activity,
                data.images[0].url,
                mBinding.ivProductImage
            )
        }
    }

}