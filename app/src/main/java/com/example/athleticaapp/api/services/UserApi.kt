package com.example.athleticaapp.api.services

import com.example.athleticaapp.api.dto.user.ChangePasswordRequest
import com.example.athleticaapp.api.dto.user.ChangePasswordResponse
import com.example.athleticaapp.api.dto.user.UpdateUserRequest
import com.example.athleticaapp.api.dto.user.UpdateUserResponse
import com.example.athleticaapp.api.dto.user.UserListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {

    @GET("users")
    suspend fun getAllUsers(): UserListResponse

    // POST: actualizar usuario (updateUsers)
    // /users/{id}
    @POST("users/{id}")
    suspend fun updateUser(
        @Path("id") id: String,
        @Body body: UpdateUserRequest
    ): UpdateUserResponse

    // POST: cambiar contraseña
    // /users/change-password
    @POST("users/change-password")
    suspend fun changePassword(
        @Body body: ChangePasswordRequest
    ): ChangePasswordResponse
}