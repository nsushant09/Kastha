package com.neupanesushant.kasthabackend.core.services

import com.neupanesushant.kasthabackend.core.repo.CartRepo
import com.neupanesushant.kasthabackend.core.repo.ProductRepo
import com.neupanesushant.kasthabackend.core.repo.UserRepo
import com.neupanesushant.kasthabackend.data.model.Cart
import com.neupanesushant.kasthabackend.data.model.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CartService(
    @Autowired private val cartRepo: CartRepo,
    @Autowired private val productRepo: ProductRepo,
    @Autowired private val userRepo: UserRepo
) {
    fun insert(productId: Int, userId: Int): Cart? {
        val user = userRepo.findById(userId).orElse(null) ?: return null
        val product = productRepo.findById(productId).orElse(null) ?: return null
        val cart = cartRepo.findByUser(user) ?: Cart(user = user)
        cart.products.add(product)
        return cartRepo.save(cart)
    }

    fun remove(productId: Int, userId: Int): Cart? {
        val user = userRepo.findById(userId).orElse(null) ?: return null
        val product = productRepo.findById(productId).orElse(null) ?: return null
        val cart = cartRepo.findByUser(user) ?: Cart(user = user)
        cart.products.remove(product)
        return cartRepo.save(cart)
    }

    fun all(userId: Int): Collection<Product>? {
        val user = userRepo.findById(userId).orElse(null) ?: return null
        val cart = cartRepo.findByUser(user)
        return cart?.products ?: emptySet()
    }
}