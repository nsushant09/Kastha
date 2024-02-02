package com.neupanesushant.kasthabackend.data.model

import jakarta.persistence.*


@Entity
@Table(name = com.neupanesushant.kasthabackend.utils.constants.Table.IMAGE)
data class Image(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Int,
    val url : String
)