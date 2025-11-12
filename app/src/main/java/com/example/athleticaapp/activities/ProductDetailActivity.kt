package com.example.athleticaapp.activities

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.athleticaapp.repositories.CartRepository
import com.example.athleticaapp.Product
import com.example.athleticaapp.R

class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val ivProductImage = findViewById<ImageView>(R.id.ivProductImageDetail)
        val tvProductName = findViewById<TextView>(R.id.tvProductNameDetail)
        val tvProductPrice = findViewById<TextView>(R.id.tvProductPriceDetail)
        val tvProductDescription = findViewById<TextView>(R.id.tvProductDescription)
        val btnAddToCart = findViewById<Button>(R.id.btnAddToCartDetail)

        // Recuperamos los datos del producto
        val name = intent.getStringExtra("name")
        val price = intent.getDoubleExtra("price", 0.0)
        val imageResId = intent.getIntExtra("imageResId", R.mipmap.ic_launcher)
        val description = intent.getStringExtra("description") ?: "Sin descripción disponible"

        // Asignamos los valores
        ivProductImage.setImageResource(imageResId)
        tvProductName.text = name
        tvProductPrice.text = "$${String.format("%,.0f", price)}"
        tvProductDescription.text = description

        btnAddToCart.setOnClickListener {
            val product = Product(name ?: "", price, imageResId, description)
            CartRepository.addToCart(product)
            Toast.makeText(this, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
        }
    }
}
