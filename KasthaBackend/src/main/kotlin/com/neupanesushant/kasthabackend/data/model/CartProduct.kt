package com.neupanesushant.kasthabackend.data.model

import com.neupanesushant.kasthabackend.utils.constants.Table
import jakarta.persistence.*


@Entity
@jakarta.persistence.Table(name = Table.CART_PRODUCT)
data class CartProduct(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "cart_id")
    val cart: Cart,

    @ManyToOne
    @JoinColumn(name = "product_id")
    val product: Product,

    var quantity: Int = 1
){
    override fun toString(): String {
        return "CartProduct(id=$id, quantity=$quantity)"
    }
}