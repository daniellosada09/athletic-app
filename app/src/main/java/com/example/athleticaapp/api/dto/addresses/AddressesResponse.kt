package com.example.athleticaapp.api.dto.addresses

data class AddressesResponse (
    val status: Int,
    val message: String,
    val data: AddressesDto
)

data class AddressesListResponse (
    val status: Int,
    val message: String,
    val data: List<AddressesDto>
)