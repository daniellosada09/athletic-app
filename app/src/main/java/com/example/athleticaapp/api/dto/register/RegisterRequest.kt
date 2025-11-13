package com.example.athleticaapp.api.dto.register

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)