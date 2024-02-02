package com.neupanesushant.kasthabackend.data.model

import com.neupanesushant.kasthabackend.utils.constants.Table
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.UniqueConstraint


@Entity
@jakarta.persistence.Table(name = Table.ROLE)
data class Role (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Int,
    @Column(unique = true)
    val name : String
)