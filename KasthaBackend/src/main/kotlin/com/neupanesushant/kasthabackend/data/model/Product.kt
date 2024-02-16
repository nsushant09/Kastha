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

    @OneToMany(
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "image_id")
    val images: List<Image>,

    @OneToOne
    @JoinColumn(name = "model_id")
    val model: ObjectModel? = null
)
