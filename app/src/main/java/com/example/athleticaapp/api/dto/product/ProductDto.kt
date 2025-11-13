package com.example.athleticaapp.api.dto.product

data class ProductDto(
    val id: String,
    val title: String,
    val description: String,
    val image: String?,      // puede ser null
    val price: Double,
    val stock: Int,
    val categoryId: String,
    val active: Boolean,
    val createdAt: String,
    val updatedAt: String
)

data class ProductListResponse(
    val status: Int,
    val message: String,
    val data: List<ProductDto>
)