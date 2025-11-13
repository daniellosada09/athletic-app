package com.example.athleticaapp.api.services

import com.example.athleticaapp.api.dto.auth.LoginRequest
import com.example.athleticaapp.api.dto.auth.LoginResponse
import com.example.athleticaapp.api.dto.register.RegisterRequest
import com.example.athleticaapp.api.dto.register.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/register")
    suspend fun register(
        @Body body: RegisterRequest
    ): RegisterResponse

    @POST("auth/login")
    suspend fun login(
        @Body body: LoginRequest
    ): LoginResponse
}