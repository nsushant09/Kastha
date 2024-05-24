package com.neupanesushant.kasthabackend.core.controller

import com.neupanesushant.kasthabackend.core.services.FavoriteService
import com.neupanesushant.kasthabackend.data.model.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/favorite")
class FavoriteController(
    @Autowired private val favoriteService: FavoriteService
) {

    @PostMapping
    fun add(
        @RequestParam("product_id") productId: Int,
        @RequestParam("user_id") userId: Int
    ): ResponseEntity<Collection<Product>> {
        val favourite =
            favoriteService.insert(productId, userId) ?: return ResponseEntity.notFound().build<Collection<Product>>()
        return ResponseEntity.ok(favourite.products)
    }

    @DeleteMapping
    fun remove(
        @RequestParam("product_ids") productIds: List<Int>,
        @RequestParam("user_id") userId: Int
    ): ResponseEntity<Collection<Product>> {
        val favourite =
            favoriteService.remove(productIds, userId) ?: return ResponseEntity.notFound().build<Collection<Product>>()
        return ResponseEntity.ok(favourite.products)
    }


    @GetMapping("/{user_id}")
    fun all(
        @PathVariable("user_id") userId: Int
    ): ResponseEntity<Collection<Product>> {
        val products = favoriteService.all(userId = userId)
        return ResponseEntity.ok(products)
    }
}