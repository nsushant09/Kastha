package com.neupanesushant.kasthabackend.data.model

import com.neupanesushant.kasthabackend.utils.constants.Table
import jakarta.persistence.*


@Entity
@jakarta.persistence.Table(name = Table.CART_PRODUCT)
data class CartProduct(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 1,

    @ManyToOne
    @JoinColumn(name = "cart_id")
    val cart: Cart,

    @ManyToOne
    @JoinColumn(name = "product_id")
    val product: Product,
    val quantity: Int = 1
)