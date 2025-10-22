package com.example.athleticaapp

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object CartManager {

    private const val PREFS_NAME = "shopping_cart"
    private const val KEY_CART = "cart_items"

    fun addToCart(context: Context, product: Product) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val currentCart = getCart(context).toMutableList()
        currentCart.add(product)
        val jsonArray = JSONArray()
        currentCart.forEach {
            val obj = JSONObject()
            obj.put("name", it.name)
            obj.put("price", it.price)
            obj.put("imageResId", it.imageResId)
            jsonArray.put(obj)
        }
        prefs.edit().putString(KEY_CART, jsonArray.toString()).apply()
    }

    fun getCart(context: Context): List<Product> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val jsonString = prefs.getString(KEY_CART, null) ?: return emptyList()
        val jsonArray = JSONArray(jsonString)
        val productList = mutableListOf<Product>()
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            productList.add(
                Product(
                    obj.getString("name"),
                    obj.getDouble("price"),
                    obj.getInt("imageResId")
                )
            )
        }
        return productList
    }

    fun clearCart(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove(KEY_CART).apply()
    }
}
