package com.neupanesushant.kasthabackend.core.controller

import com.neupanesushant.kasthabackend.core.services.CartService
import com.neupanesushant.kasthabackend.core.services.FavoriteService
import com.neupanesushant.kasthabackend.data.dtomodel.CartProductDTO
import com.neupanesushant.kasthabackend.data.mapper.Mapper
import com.neupanesushant.kasthabackend.data.model.Cart
import com.neupanesushant.kasthabackend.data.model.CartProduct
import com.neupanesushant.kasthabackend.data.model.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cart")
class CartController(
    @Autowired private val cartService: CartService
) {

    @PostMapping
    fun add(
        @RequestParam("product_id") productId: Int,
        @RequestParam("user_id") userId: Int
    ): ResponseEntity<Collection<CartProductDTO>> {
        val cart =
            cartService.insert(productId, userId) ?: return ResponseEntity.notFound()
                .build<Collection<CartProductDTO>>()
        return ResponseEntity.ok(cart.cartProducts.map(Mapper::toDto))
    }

    @DeleteMapping
    fun remove(
        @RequestParam("product_id") productId: Int,
    ): ResponseEntity<Collection<CartProductDTO>> {
        val cart =
            cartService.remove(productId) ?: return ResponseEntity.notFound().build<Collection<CartProductDTO>>()
        return ResponseEntity.ok(cart.cartProducts.map(Mapper::toDto))
    }


    @GetMapping
    fun all(
        @RequestParam("user_id") userId: Int
    ): ResponseEntity<Collection<CartProductDTO>> {
        val products = cartService.all(userId = userId)
        return ResponseEntity.ok(products?.map(Mapper::toDto))
    }
}