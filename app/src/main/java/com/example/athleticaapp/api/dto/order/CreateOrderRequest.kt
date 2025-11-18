package com.example.athleticaapp.api.dto.order

data class CreateOrderRequest (
    val userId: String,
    val addressId: String,
    val items: List<OrderItemRequest>
)

data class OrderItemRequest (
    val productId: String,
    val quantity: Number
)