package com.example.athleticaapp.repositories

import android.content.Context
import com.example.athleticaapp.api.client.ApiClient
import com.example.athleticaapp.api.dto.addresses.AddressesDto
import com.example.athleticaapp.api.dto.addresses.CreateAddressesRequest
import com.example.athleticaapp.api.services.AddressesApi

class AddressRepository(context: Context) {
    private val api: AddressesApi = ApiClient.create(context, AddressesApi::class.java)

    suspend fun createAddress(body: CreateAddressesRequest): AddressesDto {
        return api.createAddress(body).data
    }

    suspend fun updateAddress(body: CreateAddressesRequest): AddressesDto {
        return api.updateAddress(body).data
    }

    suspend fun getMyAddresses(): List<AddressesDto> {
        return api.getMyAddress().data
    }
}
