package com.example.athleticaapp

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val role: UserRole,
    val phone: String? = null,
    val address: String? = null
)
enum class UserRole {
    ADMIN,
    GENERAL
}
