package com.neupanesushant.kasthabackend.data.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = com.neupanesushant.kasthabackend.utils.constants.Table.OBJECT_MODEL)
data class ObjectModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    @Column(unique = true)
    val url: String
)