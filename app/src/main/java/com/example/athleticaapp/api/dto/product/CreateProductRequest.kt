package com.example.athleticaapp.api.dto.product

data class CreateProductRequest(
    val title: String,
    val description: String,
    val image: String,
    val price: Double,
    val stock: Int,
    val categoryId: String
)