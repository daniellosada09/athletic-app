package com.example.athleticaapp.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.athleticaapp.R
import com.example.athleticaapp.repositories.AuthRepository
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmailRegister: EditText
    private lateinit var etPasswordRegister: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvGoToLogin: TextView

    private val authRepository by lazy { AuthRepository(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Referencias
        etName = findViewById(R.id.etName)
        etEmailRegister = findViewById(R.id.etEmailRegister)
        etPasswordRegister = findViewById(R.id.etPasswordRegister)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)
        tvGoToLogin = findViewById(R.id.tvGoToLogin)

        // Acción del botón de registro
        btnRegister.setOnClickListener { registerUser() }

        // Ir al login
        tvGoToLogin.setOnClickListener {
            goToLogin()
        }
    }

    private fun registerUser() {
        val name = etName.text.toString().trim()
        val email = etEmailRegister.text.toString().trim()
        val password = etPasswordRegister.text.toString().trim()
        val confirmPassword = etConfirmPassword.text.toString().trim()

        // Validaciones
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email)
            || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)
        ) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Correo electrónico inválido", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.length < 6) {
            Toast.makeText(
                this,
                "La contraseña debe tener al menos 6 caracteres",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        // Deshabilita el botón mientras se hace la petición
        btnRegister.isEnabled = false

        // Llamada a la API con coroutines
        lifecycleScope.launch {
            try {
                val response = authRepository.register(name, email, password)

                // Aquí podrías guardar el usuario o token si lo devuelve el backend
                // Por ahora solo mostramos un mensaje
                Toast.makeText(
                    this@RegisterActivity,
                    "Registro exitoso: ${response.name}",
                    Toast.LENGTH_LONG
                ).show()

                goToLogin()

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    this@RegisterActivity,
                    "Error al registrar: ${e.message ?: "Intenta de nuevo"}",
                    Toast.LENGTH_LONG
                ).show()
            } finally {
                btnRegister.isEnabled = true
            }
        }
    }

    private fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
