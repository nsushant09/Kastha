package com.neupanesushant.kastha.extra

import com.neupanesushant.kastha.BuildConfig
import com.neupanesushant.kastha.domain.model.CartProduct
import com.neupanesushant.kastha.domain.model.Product

object Mapper {
    fun toBaseUrl(product: Product): Product {
        val imagesWithBaseUrl = product.images.map { image ->
            image.copy(url = "${BuildConfig.BASE_URL}${image.url}")
        }
        val modelWithBaseUrl = product.model?.let { model ->
            model.copy(url = "${BuildConfig.BASE_URL}${model.url}")
        }
        return product.copy(images = imagesWithBaseUrl, model = modelWithBaseUrl)
    }

    fun toBaseUrl(cartProduct: CartProduct): CartProduct {
        return cartProduct.copy(product = toBaseUrl(cartProduct.product))
    }
}