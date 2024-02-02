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
    val id : Int,
    val description : Int,
    val rating : Int,
    val date : Date,

    @ManyToOne
    @JoinColumn(name = "product_id")
    val product: Product,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user : User
)