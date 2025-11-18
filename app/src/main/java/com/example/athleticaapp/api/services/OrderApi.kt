package com.example.athleticaapp.api.services

import com.example.athleticaapp.api.dto.order.CreateOrderRequest
import com.example.athleticaapp.api.dto.order.OrderListResponse
import com.example.athleticaapp.api.dto.order.OrderResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OrderApi {

    @POST("orders")
    suspend fun createOrder(
        @Body body: CreateOrderRequest
    ): OrderResponse

    @GET("orders")
    suspend fun getAllOrders(): OrderListResponse

    @GET("orders/my")
    suspend fun getAllMyOrders(): OrderListResponse
}