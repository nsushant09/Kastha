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
import jakarta.persistence.CascadeType
import jakarta.persistence.OneToMany

@Entity
@jakarta.persistence.Table(name = Table.CART)
data class Cart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 1,

    @OneToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @OneToMany(mappedBy = "cart", cascade = [CascadeType.ALL])
    val cartProducts: MutableSet<CartProduct> = mutableSetOf()
)
