package com.neupanesushant.kastha.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class RVAdapter<T, VB : ViewDataBinding>(
    private val layoutId: Int,
    private val items: Collection<T>,
    private val bindingCallback: (mBinding: VB, data: T, datas: List<T>) -> Unit
) : RecyclerView.Adapter<RVAdapter<T, VB>.ViewHolder>() {

    private val itemsList = items.toList()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<VB>(view)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
    }

    override fun getItemCount(): Int =
        items.count()


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = itemsList[position]
        bindingCallback(holder.binding, data, itemsList)
    }

}