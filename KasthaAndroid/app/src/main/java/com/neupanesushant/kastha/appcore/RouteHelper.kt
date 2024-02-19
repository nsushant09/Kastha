package com.neupanesushant.kastha.appcore

import android.app.Activity
import androidx.core.os.bundleOf
import com.neupanesushant.kastha.core.AppConfig
import com.neupanesushant.kastha.core.Router
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.ui.activity.FullScreenContainerActivity
import com.neupanesushant.kastha.ui.fragment.main.ProductDetailFragment

object RouteHelper {
    fun routeProductDetail(activity: Activity, product: Product) {
        val routeBundle = bundleOf(
            ProductDetailFragment.PRODUCT_ARGUMENT to product
        )
        val data = bundleOf(
            FullScreenContainerActivity.FRAGMENT_ROUTE to RouteConfig.PRODUCT_DETAIL_FRAGMENT,
            FullScreenContainerActivity.FRAGMENT_ROUTE_BUNDLE to routeBundle
        )
        Router(
            activity,
            data
        ).route(AppConfig.getActivity(RouteConfig.FULL_SCREEN_CONTAINER_ACTIVITY))
    }
}