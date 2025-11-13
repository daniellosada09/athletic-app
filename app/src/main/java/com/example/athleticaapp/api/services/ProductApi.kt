package com.example.athleticaapp.api.services

import com.example.athleticaapp.api.dto.product.CreateProductRequest
import com.example.athleticaapp.api.dto.product.CreateProductResponse
import com.example.athleticaapp.api.dto.product.ProductDto
import com.example.athleticaapp.api.dto.product.ProductListResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApi {

    @GET("products")
    suspend fun getAllProducts(): ProductListResponse

    @POST("products")
    suspend fun createProduct(
        @Body body: CreateProductRequest
    ): ProductDto

    @DELETE("products/{id}")
    suspend fun deleteProduct(
        @Path("id") id: String
    )
}