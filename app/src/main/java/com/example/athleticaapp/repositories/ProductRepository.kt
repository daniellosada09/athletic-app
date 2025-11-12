package com.example.athleticaapp.repositories

import com.example.athleticaapp.Product
import com.example.athleticaapp.R

object ProductRepository {
    private val products = mutableListOf<Product>()

    init {
        // Productos por defecto
        products.addAll(
            listOf(
                Product("Camiseta Deportiva", 59900.0, R.mipmap.ic_launcher),
                Product("Tenis Running", 199900.0, R.mipmap.ic_launcher),
                Product("Balón de Fútbol", 89000.0, R.mipmap.ic_launcher),
                Product("Guantes de Gym", 35000.0, R.mipmap.ic_launcher),
                Product("Short Deportivo", 45000.0, R.mipmap.ic_launcher)
            )
        )
    }

    fun getAllProducts(): List<Product> = products

    fun addProduct(product: Product) {
        products.add(product)
    }

    fun updateProduct(index: Int, updatedProduct: Product) {
        if (index in products.indices) {
            products[index] = updatedProduct
        }
    }

    fun deleteProduct(index: Int) {
        if (index in products.indices) {
            products.removeAt(index)
        }
    }
}
