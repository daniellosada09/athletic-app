package com.example.athleticaapp.repositories

import android.content.Context
import android.util.Log
import com.example.athleticaapp.api.client.ApiClient
import com.example.athleticaapp.api.dto.user.UserListItemDto
import com.example.athleticaapp.api.services.UserApi

class RemoteUserRepository(context: Context) {

    private val service: UserApi = ApiClient.create(context, UserApi::class.java)

    suspend fun getAllUsers(): List<UserListItemDto> {
        return try {
            val response = service.getAllUsers()

            Log.d(
                "RemoteUserRepository",
                "✔ getAllUsers() OK: ${response.data.size} usuarios"
            )

            response.data

        } catch (e: Exception) {
            Log.e("RemoteUserRepository", "❌ Error en getAllUsers()", e)
            throw e
        }
    }
}
