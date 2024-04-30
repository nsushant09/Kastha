package com.neupanesushant.kasthabackend.data.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import java.sql.Date

@Entity

data class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val description: String,
    val rating: Int,
    var date: String,

    @ManyToOne
    @JoinColumn(name = "product_id")
    var product: Product? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User? = null
)