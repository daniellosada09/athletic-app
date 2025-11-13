package com.example.athleticaapp

import android.content.Context
import com.example.athleticaapp.api.dto.product.ProductDto
import org.json.JSONArray
import org.json.JSONObject

object CartManager {

    private const val PREFS_NAME = "shopping_cart"
    private const val KEY_CART = "cart_items"

    // ============================
    // AGREGAR AL CARRITO
    // ============================
    fun addToCart(context: Context, product: ProductDto) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val currentCart = getCart(context).toMutableList()

        currentCart.add(product)

        val jsonArray = JSONArray()

        currentCart.forEach {
            val obj = JSONObject()

            obj.put("id", it.id)
            obj.put("title", it.title)
            obj.put("description", it.description)
            obj.put("image", it.image ?: "")
            obj.put("price", it.price)
            obj.put("stock", it.stock)
            obj.put("categoryId", it.categoryId)

            jsonArray.put(obj)
        }

        prefs.edit().putString(KEY_CART, jsonArray.toString()).apply()
    }

    // ============================
    // OBTENER CARRITO
    // ============================
    fun getCart(context: Context): List<ProductDto> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val jsonString = prefs.getString(KEY_CART, null) ?: return emptyList()

        val jsonArray = JSONArray(jsonString)
        val productList = mutableListOf<ProductDto>()

        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)

            val product = ProductDto(
                id = obj.getString("id"),
                title = obj.getString("title"),
                description = obj.optString("description"),
                image = obj.optString("image", null),
                price = obj.getDouble("price"),
                stock = obj.optInt("stock", 0),
                categoryId = obj.optString("categoryId"),
                active = true, // el carrito no requiere este valor
                createdAt = "",
                updatedAt = ""
            )

            productList.add(product)
        }

        return productList
    }

    // ============================
    // LIMPIAR CARRITO
    // ============================
    fun clearCart(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove(KEY_CART).apply()
    }
}
