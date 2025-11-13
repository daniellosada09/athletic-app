package com.example.athleticaapp.api.dto.auth

data class LoginResponse(
    val status: Int,
    val message: String,
    val data: LoginDataDto
)

data class LoginDataDto(
    val token: String,
    val user: LoginUserDto
)

data class LoginUserDto(
    val id: String,
    val name: String,
    val email: String,
    val photoUrl: String?,
    val role: RoleDto,
    val createdAt: String,
    val updatedAt: String,
    val lastLogin: String?,
    val isActive: Boolean
)

data class RoleDto(
    val id: Int,
    val name: String,
    val description: String
)
