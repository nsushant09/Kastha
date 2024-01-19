package com.neupanesushant.kasthabackend.data.model

import jakarta.persistence.*

@Entity
@Table(name = com.neupanesushant.kasthabackend.utils.constants.Table.PRODUCT)
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val name: String,
    val description: String,
    val price: Float,

    @ManyToOne
    @JoinColumn(name = "product_id")
    val imagesUrl: List<String>,

    val modelObjectUrl: String
)
