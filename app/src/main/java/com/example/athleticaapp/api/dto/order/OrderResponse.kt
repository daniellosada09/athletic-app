package com.example.athleticaapp.api.dto.order

data class OrderResponse (
    val status: Int,
    val message: String,
    val data: OrderDto
)

data class OrderListResponse (
    val status: Int,
    val message: String,
    val data: List<OrderDto>
)