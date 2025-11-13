package com.example.athleticaapp.api.dto.user

data class ChangePasswordRequest(
    val email: String,
    val currentPassword: String,
    val newPassword: String
)