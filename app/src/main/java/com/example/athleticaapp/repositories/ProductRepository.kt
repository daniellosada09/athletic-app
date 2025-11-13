package com.example.athleticaapp.repositories

import android.content.Context
import com.example.athleticaapp.api.client.ApiClient
import com.example.athleticaapp.api.dto.product.CreateProductRequest
import com.example.athleticaapp.api.dto.product.ProductDto
import com.example.athleticaapp.api.services.ProductApi

class ProductRepository(context: Context) {

    private val api = ApiClient.create(context, ProductApi::class.java)

    suspend fun fetchAllProducts(): List<ProductDto> {
        val response = api.getAllProducts()
        return response.data // tu API devuelve {status, message, data}
    }

    suspend fun createProduct(
        title: String,
        description: String,
        price: Double,
        stock: Int,
        categoryId: String,
        image: String
    ): ProductDto {

        val request = CreateProductRequest(
            title = title,
            description = description,
            price = price,
            stock = stock,
            categoryId = categoryId,
            image = image
        )

        val response = api.createProduct(request)
        return response
    }

    suspend fun deleteProduct(id: String) {
        api.deleteProduct(id)
    }
}
