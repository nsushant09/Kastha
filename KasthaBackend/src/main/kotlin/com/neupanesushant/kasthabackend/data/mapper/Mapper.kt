package com.neupanesushant.kasthabackend.data.mapper

import com.neupanesushant.kasthabackend.data.dtomodel.CartProductDTO
import com.neupanesushant.kasthabackend.data.dtomodel.ReviewDTO
import com.neupanesushant.kasthabackend.data.dtomodel.UserDTO
import com.neupanesushant.kasthabackend.data.model.CartProduct
import com.neupanesushant.kasthabackend.data.model.Review
import com.neupanesushant.kasthabackend.data.model.User

object Mapper {
    fun toDto(cartProduct: CartProduct): CartProductDTO =
        CartProductDTO(cartProduct.id ?: 0, cartProduct.product, cartProduct.quantity)

    fun toDto(user: User): UserDTO =
        UserDTO(user.id, user.firstName, user.lastName, user.email, user.location, user.gender, user.roles)

    fun toDto(review: Review): ReviewDTO =
        ReviewDTO(
            review.id,
            review.description,
            review.rating,
            review.date,
            "${review.user?.firstName} ${review.user?.lastName}"
        )
}