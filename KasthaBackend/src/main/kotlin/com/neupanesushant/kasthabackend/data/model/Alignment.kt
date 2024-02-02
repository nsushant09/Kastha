package com.neupanesushant.kasthabackend.data.model

import com.neupanesushant.kasthabackend.utils.constants.Table
import jakarta.persistence.*

@Entity
@jakarta.persistence.Table(name = Table.ALIGNMENT)
data class Alignment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Int,
    @Column(unique = true)
    val name : String
)