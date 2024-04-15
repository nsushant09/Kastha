package com.neupanesushant.kasthabackend.data.model

import jakarta.persistence.*

@Entity
@Table(name = com.neupanesushant.kasthabackend.utils.constants.Table.PRODUCT)
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val name: String,
    @Column(columnDefinition = "TEXT")
    val description: String,
    val price: Float,
    val stockQuantity: Int,

    @OneToMany(
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "product_id")
    val images: List<Image>,

    @OneToOne
    @JoinColumn(name = "model_id")
    val model: ObjectModel? = null,

    @ManyToOne
    @JoinColumn(name = "category_id")
    val category: Category
)
