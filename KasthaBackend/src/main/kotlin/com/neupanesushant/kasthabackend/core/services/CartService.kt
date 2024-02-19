package com.neupanesushant.kasthabackend.core.services

import com.neupanesushant.kasthabackend.core.repo.CartProductRepo
import com.neupanesushant.kasthabackend.core.repo.CartRepo
import com.neupanesushant.kasthabackend.core.repo.ProductRepo
import com.neupanesushant.kasthabackend.core.repo.UserRepo
import com.neupanesushant.kasthabackend.data.model.Cart
import com.neupanesushant.kasthabackend.data.model.CartProduct
import com.neupanesushant.kasthabackend.data.model.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class CartService(
    @Autowired private val cartRepo: CartRepo,
    @Autowired private val productRepo: ProductRepo,
    @Autowired private val cartProductRepo: CartProductRepo,
    @Autowired private val userRepo: UserRepo
) {
    fun insert(productId: Int, userId: Int): Cart? {
        val user = userRepo.findById(userId).getOrNull() ?: return null
        val product = productRepo.findById(productId).getOrNull() ?: return null
        val cart = cartRepo.findByUser(user) ?: Cart(user = user)
        cart.cartProducts.add(CartProduct(cart = cart, product = product))
        return cartRepo.save(cart)
    }

    fun remove(cartProductId: Int): Cart? {
        val cartProduct = cartProductRepo.findById(cartProductId).getOrNull() ?: return null
        val cart = cartProduct.cart
        return cartRepo.save(cart)
    }

    fun all(userId: Int): Collection<CartProduct>? {
        val user = userRepo.findById(userId).orElse(null) ?: return null
        val cart = cartRepo.findByUser(user)
        return cart?.cartProducts ?: emptySet()
    }
}