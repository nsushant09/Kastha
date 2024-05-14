package com.neupanesushant.kastha.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.neupanesushant.kastha.appcore.RouteHelper
import com.neupanesushant.kastha.databinding.ItemLargeProductCardBinding
import com.neupanesushant.kastha.domain.managers.GlideManager
import com.neupanesushant.kastha.domain.managers.PaletteManager
import com.neupanesushant.kastha.domain.model.Product

class LargeProductCardAdapter(
    private val activity: Activity,
    private val products: List<Product>,
    private val itemWidth: Int
) : RecyclerView.Adapter<LargeProductCardAdapter.ViewHolder>() {

    private var renderableProducts = products.take(PAGE_SIZE).toMutableList()
    private val itemHeight = (itemWidth * 4) / 3
    private var isLoading = false
    private var currentPage = 1

    companion object {
        private const val PAGE_SIZE = 10
    }

    inner class ViewHolder(val binding: ItemLargeProductCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLargeProductCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = renderableProducts.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mBinding = holder.binding
        val data = renderableProducts[position]

        holder.itemView.layoutParams.width = itemWidth
        mBinding.cvProductImage.layoutParams.height = itemHeight

        mBinding.tvProductTitle.text = data.name
        mBinding.tvProductPrice.text = "Rs." + data.price
        mBinding.layoutArFeatured.cvArFeatured.isVisible = data.model != null
        mBinding.root.setOnClickListener {
            RouteHelper.routeProductDetail(activity, data)
        }

        if (data.images.isNotEmpty()) {
            GlideManager.loadWithBitmap(
                mBinding.ivProductImage.context,
                data.images[0].url,
            ) { bitmap, _ ->
                PaletteManager.setBackgroundDynamically(
                    mBinding.cvProductImage.context,
                    mBinding.cvProductImage,
                    bitmap
                )
                mBinding.ivProductImage.setImageBitmap(bitmap)
            }
        }
    }

    fun loadMoreItems() {
        if (isLoading) return

        val start = currentPage * PAGE_SIZE
        if (start >= products.size) return

        isLoading = true
        currentPage++

        val nextItems = products.drop(start).take(PAGE_SIZE)
        renderableProducts.addAll(nextItems)
        notifyItemRangeInserted(start, nextItems.size)
        isLoading = false
    }
}
