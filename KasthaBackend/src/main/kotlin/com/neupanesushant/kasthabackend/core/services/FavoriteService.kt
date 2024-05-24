package com.neupanesushant.kasthabackend.core.services

import com.neupanesushant.kasthabackend.core.repo.FavoriteRepo
import com.neupanesushant.kasthabackend.core.repo.ProductRepo
import com.neupanesushant.kasthabackend.core.repo.UserRepo
import com.neupanesushant.kasthabackend.data.model.Favorite
import com.neupanesushant.kasthabackend.data.model.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FavoriteService(
    @Autowired private val favoriteRepo: FavoriteRepo,
    @Autowired private val productRepo: ProductRepo,
    @Autowired private val userRepo: UserRepo
) {
    fun insert(productId: Int, userId: Int): Favorite? {
        val user = userRepo.findById(userId).orElse(null) ?: return null
        val product = productRepo.findById(productId).orElse(null) ?: return null
        val favorite = favoriteRepo.findByUser(user) ?: Favorite(user = user)
        favorite.products.add(product)
        return favoriteRepo.save(favorite)
    }

    fun remove(productIds: List<Int>, userId: Int): Favorite? {
        val user = userRepo.findById(userId).orElse(null) ?: return null
        val favorite = favoriteRepo.findByUser(user) ?: Favorite(user = user)

        productIds.forEach { productId ->
            val product = productRepo.findById(productId).orElse(null) ?: return null
            favorite.products.remove(product)
        }
        return favoriteRepo.save(favorite)
    }

    fun all(userId: Int): Collection<Product>? {
        val user = userRepo.findById(userId).orElse(null) ?: return null
        val favorite = favoriteRepo.findByUser(user)
        return favorite?.products ?: emptySet()
    }
}