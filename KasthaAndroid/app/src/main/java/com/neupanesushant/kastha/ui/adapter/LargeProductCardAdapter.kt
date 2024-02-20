package com.neupanesushant.kastha.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.neupanesushant.kastha.appcore.RouteHelper
import com.neupanesushant.kastha.databinding.ItemLargeProductCardBinding
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.domain.usecase.managers.GlideManager
import com.neupanesushant.kastha.domain.usecase.managers.PaletteManager
import com.neupanesushant.kastha.extra.extensions.dpToPx

class LargeProductCardAdapter(
    private val activity: Activity,
    private val products: List<Product>
) : RecyclerView.Adapter<LargeProductCardAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemLargeProductCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLargeProductCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int =
        products.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mBinding = holder.binding
        val data = products[position]
        holder.itemView.layoutParams.width =
            ((Resources.getSystem().displayMetrics.widthPixels / 2) - dpToPx(
                activity,
                24f
            )).toInt()

        mBinding.cvProductImage.layoutParams.height = (mBinding.root.layoutParams.width * 4) / 3

        mBinding.tvProductTitle.text = data.name
        mBinding.tvProductPrice.text = "Rs." + data.price
        mBinding.layoutArFeatured.cvArFeatured.isVisible = data.model != null
        mBinding.root.setOnClickListener {
            RouteHelper.routeProductDetail(activity, data)
        }

        if (data.images.isNotEmpty()) {
            GlideManager.loadWithBitmap(
                activity,
                data.images.shuffled()[0].url
            ) { bitmap, _ ->
                PaletteManager.setBackgroundDynamically(
                    activity,
                    mBinding.cvProductImage,
                    bitmap
                )
                mBinding.ivProductImage.setImageBitmap(bitmap)
            }
        }
    }
}