package com.example.athleticaapp.ui.theme.inicio

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.athleticaapp.activities.LoginActivity
import com.example.athleticaapp.activities.RegisterActivity
import com.example.athleticaapp.databinding.ActivityInicioBinding

class InicioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inicializarEventos()
    }

    private fun inicializarEventos() {
        // Botón para ir al Login
        binding.btnIrLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Botón para ir al Registro
        binding.btnIrRegistro.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Versión app (solo muestra mensaje)
        binding.tvVersionApp.setOnClickListener {
            Toast.makeText(this, "Versión 1.0.0", Toast.LENGTH_SHORT).show()
        }
    }
}