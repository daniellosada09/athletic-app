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

class RegisterActivity : AppCompatActivity() {
    private var etName: EditText? = null
    private var etEmailRegister: EditText? = null
    private var etPasswordRegister: EditText? = null
    private var etConfirmPassword: EditText? = null
    private var btnRegister: Button? = null
    private var tvGoToLogin: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Referenciar vistas
        etName = findViewById<EditText?>(R.id.etName)
        etEmailRegister = findViewById<EditText?>(R.id.etEmailRegister)
        etPasswordRegister = findViewById<EditText?>(R.id.etPasswordRegister)
        etConfirmPassword = findViewById<EditText?>(R.id.etConfirmPassword)
        btnRegister = findViewById<Button?>(R.id.btnRegister)
        tvGoToLogin = findViewById<TextView?>(R.id.tvGoToLogin)

        // Acción del botón de registro
        btnRegister!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                registerUser()
            }
        })

        // Ir al login
        tvGoToLogin!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intent: Intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    private fun registerUser() {
        val name = etName!!.getText().toString().trim { it <= ' ' }
        val email = etEmailRegister!!.getText().toString().trim { it <= ' ' }
        val password = etPasswordRegister!!.getText().toString().trim { it <= ' ' }
        val confirmPassword = etConfirmPassword!!.getText().toString().trim { it <= ' ' }

        // Validaciones básicas
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) ||
            TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)
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

        // Aquí podrías guardar los datos en una base de datos local o Firebase
        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show()

        // Redirigir al login
        val intent: Intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}