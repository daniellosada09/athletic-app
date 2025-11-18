package com.example.athleticaapp.api.dto.addresses

import java.time.LocalDateTime

data class AddressesDto (
    val id: String,
    val userId: String,
    val label: String,
    val addressLine1: String,
    val addressLine2: String,
    val cityId: String,
    val cityName: String,
    val state: String,
    val postalCode: String,
    val phone: String,
    val createdAt: String
)
