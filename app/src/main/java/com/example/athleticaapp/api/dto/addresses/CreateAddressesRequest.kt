package com.example.athleticaapp.api.dto.addresses

data class CreateAddressesRequest (
    val userId: String,
    val label: String,
    val addressLine1: String,
    val addressLine2: String,
    val cityId: String,
    val state: String,
    val postalCode: String,
    val phone: String
)