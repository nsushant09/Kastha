package com.neupanesushant.kasthabackend.data.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToOne
import com.neupanesushant.kasthabackend.utils.constants.Table

@Entity
@jakarta.persistence.Table(name = Table.CART)
data class Cart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Int,

    @OneToOne
    @JoinColumn(name = "user_id")
    val user : User,

    @ManyToMany
    @JoinTable(
        name = Table.CART_PRODUCT,
        joinColumns = [JoinColumn(name = "cart_id")],
        inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    val products : Set<Product> = linkedSetOf()
)
