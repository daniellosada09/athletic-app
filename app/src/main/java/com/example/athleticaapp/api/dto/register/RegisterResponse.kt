package com.example.athleticaapp.api.dto.register

data class RegisterResponse(
    val id: Int,
    val name: String,
    val email: String,
    val token: String
)
