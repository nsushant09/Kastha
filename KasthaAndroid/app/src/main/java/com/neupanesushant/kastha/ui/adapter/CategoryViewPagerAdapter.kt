package com.neupanesushant.kastha.ui.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.neupanesushant.kastha.domain.model.Category
import com.neupanesushant.kastha.ui.fragment.main.CategoriesViewPagerFragment

class CategoryViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val categories: List<Category>
) : FragmentStateAdapter(
    fragmentManager,
    lifecycle
) {
    override fun getItemCount(): Int {
        if (categories.isEmpty())
            return 0
        return categories.count()
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = CategoriesViewPagerFragment()
        fragment.arguments = bundleOf("category" to categories[position])
        return fragment
    }

}