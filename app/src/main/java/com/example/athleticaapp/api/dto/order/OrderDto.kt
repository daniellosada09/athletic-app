package com.example.athleticaapp.api.dto.order

data class OrderDto(
    val id: String? = null,
    val userId: String? = null,
    val addressId: String? = null,
    val total: Double? = null,
    val createdAt: String? = null,
    val items: List<OrderItemDto> = emptyList(),
    val status: OrderStatus? = null
)

data class OrderItemDto(
    val productId: String? = null,
    val productName: String? = null,
    val quantity: Int = 0,
    val price: Double? = null
)

enum class OrderStatus {
    CREATED, PAID, PROCESSING, SHIPPED, DELIVERED, CANCELLED, REFUNDED
}
