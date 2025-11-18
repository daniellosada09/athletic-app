package com.example.athleticaapp.repositories

import android.content.Context
import com.example.athleticaapp.api.client.ApiClient
import com.example.athleticaapp.api.dto.order.CreateOrderRequest
import com.example.athleticaapp.api.dto.order.OrderDto
import com.example.athleticaapp.api.services.OrderApi

class OrderRepository (context: Context){
    private val api: OrderApi = ApiClient.create(context, OrderApi::class.java)

    suspend fun createOrder(body: CreateOrderRequest): OrderDto {
        return api.createOrder(body).data
    }

    suspend fun getMyOrders(): List<OrderDto> {
        return api.getAllMyOrders().data
    }
}