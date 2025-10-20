package com.example.athleticaapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnLogin: Button? = null
    private var tvRegister: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Referencias de las vistas
        etEmail = findViewById<EditText?>(R.id.etEmail)
        etPassword = findViewById<EditText?>(R.id.etPassword)
        btnLogin = findViewById<Button?>(R.id.btnLogin)
        tvRegister = findViewById<TextView?>(R.id.tvRegister)

        // Acción al presionar "Ingresar"
        btnLogin!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                loginUser()
            }
        })

        // Ir al registro
        tvRegister!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        })
    }

    private fun loginUser() {
        val email = etEmail!!.getText().toString().trim { it <= ' ' }
        val password = etPassword!!.getText().toString().trim { it <= ' ' }

        // Validaciones básicas
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor ingresa correo y contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Correo electrónico inválido", Toast.LENGTH_SHORT).show()
            return
        }

        // Autenticación simulada
        // En un caso real, validarías contra tu backend o base de datos
        if (email == "admin@demo.com" && password == "123456") {
            Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()

            // Redirigir al dashboard o pantalla principal
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
        }
    }
}