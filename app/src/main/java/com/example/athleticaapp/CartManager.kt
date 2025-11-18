package com.example.athleticaapp

import android.content.Context
import com.example.athleticaapp.api.dto.product.ProductDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object CartManager {

    private const val PREF_NAME = "cart_prefs"
    private const val CART_KEY = "cart_items"

    fun addToCart(context: Context, product: ProductDto) {
        val cart = getCart(context).toMutableList()
        cart.add(product)
        saveCart(context, cart)
    }

    fun getCart(context: Context): List<ProductDto> {
        val shared = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = shared.getString(CART_KEY, null) ?: return emptyList()
        val type = object : TypeToken<List<ProductDto>>() {}.type
        return Gson().fromJson(json, type)
    }

    fun clearCart(context: Context) {
        val shared = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        shared.edit().remove(CART_KEY).apply()
    }

    fun saveCart(context: Context, cart: List<ProductDto>) {
        val shared = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = Gson().toJson(cart)
        shared.edit().putString(CART_KEY, json).apply()
    }
}

