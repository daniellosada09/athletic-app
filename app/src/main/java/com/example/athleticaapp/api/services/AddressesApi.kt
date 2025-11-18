package com.example.athleticaapp.api.services

import com.example.athleticaapp.api.dto.addresses.AddressesDto
import com.example.athleticaapp.api.dto.addresses.AddressesListResponse
import com.example.athleticaapp.api.dto.addresses.AddressesResponse
import com.example.athleticaapp.api.dto.addresses.CreateAddressesRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface AddressesApi {

    @POST("addresses")
    suspend fun createAddress(
        @Body body: CreateAddressesRequest
    ): AddressesResponse

    @PUT("addresses/login")
    suspend fun updateAddress(
        @Body body: CreateAddressesRequest
    ): AddressesResponse

    @GET("addresses")
    suspend fun getMyAddress(): AddressesListResponse

}