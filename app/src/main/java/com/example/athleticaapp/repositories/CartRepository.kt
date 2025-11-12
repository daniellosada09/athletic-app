package com.example.athleticaapp.repositories

import com.example.athleticaapp.Product

object CartRepository {
    private val cartItems = mutableListOf<Product>()

    fun addToCart(product: Product) {
        cartItems.add(product)
    }

    fun getCart(): List<Product> = cartItems

    fun clearCart() {
        cartItems.clear()
    }

    fun getTotal(): Double = cartItems.sumOf { it.price }
}
