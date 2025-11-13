package com.example.athleticaapp.api.dto.user

data class UpdateUserRequest(
    val name: String?,
    val email: String?,
    // agrega aquí los campos editables del usuario
)