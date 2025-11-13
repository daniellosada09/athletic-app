package com.example.athleticaapp.repositories

import android.content.Context
import com.example.athleticaapp.api.client.ApiClient
import com.example.athleticaapp.api.dto.auth.LoginRequest
import com.example.athleticaapp.api.dto.auth.LoginResponse
import com.example.athleticaapp.api.dto.register.RegisterRequest
import com.example.athleticaapp.api.dto.register.RegisterResponse
import com.example.athleticaapp.api.services.AuthApi

class AuthRepository(context: Context) {

    // ApiClient ahora requiere context para manejar token
    private val api: AuthApi = ApiClient.create(context, AuthApi::class.java)

    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        val body = RegisterRequest(
            name = name,
            email = email,
            password = password
        )
        return api.register(body)
    }

    suspend fun login(email: String, password: String): LoginResponse {
        val body = LoginRequest(
            email = email,
            password = password
        )
        return api.login(body)
    }
}
