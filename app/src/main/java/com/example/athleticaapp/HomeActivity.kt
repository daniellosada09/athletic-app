package com.example.athleticaapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home) // tu layout del home

        // RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Lista de prueba
        val productList = listOf(
            Product("Camiseta Deportiva", 59900.0, R.mipmap.ic_launcher),
            Product("Tenis Running", 199900.0, R.mipmap.ic_launcher),
            Product("Balón de Fútbol", 89000.0, R.mipmap.ic_launcher),
            Product("Guantes de Gym", 35000.0, R.mipmap.ic_launcher),
            Product("Short Deportivo", 45000.0, R.mipmap.ic_launcher)
        )

        // Adaptador
        recyclerView.adapter = ProductAdapter(productList)
    }
}
