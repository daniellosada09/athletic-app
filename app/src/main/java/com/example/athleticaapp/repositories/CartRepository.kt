package com.example.athleticaapp.repositories

import com.example.athleticaapp.api.dto.product.ProductDto

object CartRepository {
    private val cartItems = mutableListOf< ProductDto>()

    fun addToCart(product: ProductDto) {
        cartItems.add(product)
    }

    fun getCart(): List<ProductDto> = cartItems

    fun clearCart() {
        cartItems.clear()
    }

    fun getTotal(): Double = cartItems.sumOf { it.price }
}
