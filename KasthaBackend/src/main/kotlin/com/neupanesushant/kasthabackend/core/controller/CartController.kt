package com.neupanesushant.kasthabackend.core.controller

import com.neupanesushant.kasthabackend.core.services.CartService
import com.neupanesushant.kasthabackend.core.services.FavoriteService
import com.neupanesushant.kasthabackend.data.dtomodel.CartProductDTO
import com.neupanesushant.kasthabackend.data.mapper.Mapper
import com.neupanesushant.kasthabackend.data.model.BaseResponse
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
        val cartProducts =
            cartService.insert(productId, userId) ?: return ResponseEntity.notFound()
                .build()
        return ResponseEntity.ok(cartProducts.map(Mapper::toDto))
    }

    @DeleteMapping
    fun remove(
        @RequestParam("cart_product_ids") cartProductIds: List<Int>,
    ): ResponseEntity<Collection<CartProductDTO>> {
        val cartProducts =
            cartService.remove(cartProductIds) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(cartProducts.map(Mapper::toDto))
    }


    @GetMapping("/{user_id}")
    fun all(
        @PathVariable("user_id") userId: Int
    ): ResponseEntity<Collection<CartProductDTO>> {
        val products = cartService.all(userId = userId)
        return ResponseEntity.ok(products?.map(Mapper::toDto))
    }

    @PostMapping("/increment/{cart_product_id}")
    fun increment(
        @PathVariable("cart_product_id") cartProductId: Int
    ): ResponseEntity<CartProductDTO> {
        val cartProduct = cartService.incrementCartProduct(cartProductId) ?: return ResponseEntity.notFound()
            .build()
        return ResponseEntity.ok(Mapper.toDto(cartProduct))
    }

    @PostMapping("/decrement/{cart_product_id}")
    fun decrement(
        @PathVariable("cart_product_id") cartProductId: Int
    ): ResponseEntity<CartProductDTO> {
        val cartProduct = cartService.decrementCartProduct(cartProductId) ?: return ResponseEntity.notFound()
            .build()
        return ResponseEntity.ok(Mapper.toDto(cartProduct))
    }
}