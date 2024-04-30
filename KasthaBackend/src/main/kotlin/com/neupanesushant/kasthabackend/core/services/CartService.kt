package com.neupanesushant.kasthabackend.core.services

import com.neupanesushant.kasthabackend.core.repo.CartProductRepo
import com.neupanesushant.kasthabackend.core.repo.CartRepo
import com.neupanesushant.kasthabackend.core.repo.ProductRepo
import com.neupanesushant.kasthabackend.core.repo.UserRepo
import com.neupanesushant.kasthabackend.data.model.Cart
import com.neupanesushant.kasthabackend.data.model.CartProduct
import com.neupanesushant.kasthabackend.data.model.Product
import jakarta.transaction.Transactional
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

    private fun getCart(userId: Int): Cart? {
        val user = userRepo.findById(userId).getOrNull() ?: return null
        val cart = cartRepo.findByUser(user) ?: Cart(user = user)
        return cart
    }

    @Transactional
    fun insert(productId: Int, userId: Int): Collection<CartProduct>? {
        val product = productRepo.findById(productId).orElse(null) ?: return null
        val cart = getCart(userId) ?: return null

        if (cart.products.find { it.product.id == product.id } == null) {
            val cartProduct = CartProduct(product = product, cart = cart)
            val savedCartProduct = cartProductRepo.save(cartProduct)
            cart.products.add(savedCartProduct)
        }

        val savedCart = cartRepo.save(cart)

        return savedCart.products
    }

    @Transactional
    fun remove(cartProductIds: List<Int>): Collection<CartProduct>? {
        val firstCartProduct = cartProductRepo.findById(cartProductIds.first()).orElse(null) ?: return null
        val cart = firstCartProduct.cart
        cartProductIds.forEach { cartProductId ->
            val cartProduct = cartProductRepo.findById(cartProductId).getOrNull()
            cartProduct?.let {
                cartProductRepo.delete(it)
                cart.products.remove(it)
            }
        }
        val savedCart = cartRepo.save(cart)
        return savedCart.products
    }

    fun all(userId: Int): Collection<CartProduct>? {
        val user = userRepo.findById(userId).orElse(null) ?: return null
        val cart = cartRepo.findByUser(user)
        return cart?.products ?: emptySet()
    }

    fun incrementCartProduct(cartProductId: Int): CartProduct? {
        val cartProduct = cartProductRepo.findById(cartProductId).getOrNull() ?: return null
        cartProduct.copy(quantity = cartProduct.quantity++)
        cartProductRepo.save(cartProduct)
        return cartProduct
    }

    fun decrementCartProduct(cartProductId: Int): CartProduct? {
        val cartProduct = cartProductRepo.findById(cartProductId).getOrNull() ?: return null
        cartProduct.copy(quantity = cartProduct.quantity--)
        cartProductRepo.save(cartProduct)
        return cartProduct
    }
}