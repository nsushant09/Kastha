package com.neupanesushant.kastha.appcore

import android.app.Activity
import android.os.Bundle
import androidx.core.os.bundleOf
import com.neupanesushant.kastha.core.AppConfig
import com.neupanesushant.kastha.core.Router
import com.neupanesushant.kastha.domain.model.Alignment
import com.neupanesushant.kastha.domain.model.ObjectModel
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.ui.activity.FullScreenContainerActivity
import com.neupanesushant.kastha.ui.fragment.main.AugmentedViewFragment
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

    fun routeFullScreenContainerActivity(
        activity: Activity,
        fragmentRoute: String,
        fragmentData: Bundle? = null
    ) {
        val data = bundleOf()
        data.putString(FullScreenContainerActivity.FRAGMENT_ROUTE, fragmentRoute)

        fragmentData?.let {
            data.putBundle(FullScreenContainerActivity.FRAGMENT_ROUTE_BUNDLE, it)
        }

        Router(
            activity,
            data
        ).route(AppConfig.getActivity(RouteConfig.FULL_SCREEN_CONTAINER_ACTIVITY))
    }

    fun routeAugmentedView(activity: Activity, objectModel: ObjectModel, alignment: Alignment) {
        val data = bundleOf(
            AugmentedViewFragment.MODEL_ARGUMENT to objectModel,
            AugmentedViewFragment.ALIGNMENT_ARGUMENT to alignment
        )
        Router(
            activity,
            data
        ).route(AppConfig.getActivity(RouteConfig.AUGMENTED_VIEW_ACTIVITY))
    }
}