package com.example.athleticaapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RecoverPasswordActivity : AppCompatActivity() {

    private lateinit var etRecoverEmail: EditText
    private lateinit var btnNextRecover: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_password)

        etRecoverEmail = findViewById(R.id.etRecoverEmail)
        btnNextRecover = findViewById(R.id.btnNextRecover)

        btnNextRecover.setOnClickListener {
            val email = etRecoverEmail.text.toString().trim()

            if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Ingrese un correo válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 🔹 Buscar el usuario en el repositorio
            val user = UserRepository.getAllUsers().find { it.email == email }

            if (user != null) {
                // Si existe el correo, redirigir al cambio de contraseña
                val intent = Intent(this, ResetPasswordActivity::class.java)
                intent.putExtra("email", email)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Correo no encontrado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
