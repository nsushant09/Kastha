package com.neupanesushant.kasthabackend.data.model

import com.neupanesushant.kasthabackend.utils.constants.Table
import jakarta.persistence.*

@Entity
@jakarta.persistence.Table(name = Table.FAVORITE)
data class Favorite(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Int = -1,

    @OneToOne
    @JoinColumn(name = "user_id")
    val user : User,

    @ManyToMany
    @JoinTable(
        name = Table.CART_PRODUCT,
        joinColumns = [JoinColumn(name = "favorite_id")],
        inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    val products : MutableCollection<Product> = mutableSetOf()
)
