package com.neupanesushant.kasthabackend.data.model

data class Product(
    val id : Int,
    val name : String,
    val description : String,
    val price : Float,
    val images : List<String>,
    val modelObject : String
)

// Add Stock
