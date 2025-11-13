package com.example.athleticaapp.api.client

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // 🔥 1. Rutas que NO llevan token
        val excludedPaths = listOf("/auth/login", "/auth/register")

        if (excludedPaths.any { request.url.encodedPath.endsWith(it) }) {
            return chain.proceed(request)
        }

        // 🔥 2. Obtener token
        val token = AuthTokenManager.getToken(context)

        // Si no hay token → dejar pasar la petición normal
        if (token.isNullOrEmpty()) {
            return chain.proceed(request)
        }

        // 🔥 3. Agregar token
        val newRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(newRequest)
    }
}
