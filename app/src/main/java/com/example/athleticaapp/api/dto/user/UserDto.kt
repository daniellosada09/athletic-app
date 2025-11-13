package com.example.athleticaapp.api.dto.user

data class UserListResponse(
    val status: Int,
    val message: String,
    val data: List<UserListItemDto>
)

data class UserListItemDto(
    val id: String,
    val name: String,
    val email: String,
    val roleName: String,
    val isActive: Boolean
)