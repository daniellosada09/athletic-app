package com.example.athleticaapp.api.dto.user

data class UpdateUserResponse(
    val id: String,
    val name: String,
    val email: String,
    // agrega más campos si tu API los devuelve
)