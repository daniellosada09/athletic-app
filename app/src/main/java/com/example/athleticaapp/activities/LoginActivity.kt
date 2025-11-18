package com.example.athleticaapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.athleticaapp.R
import com.example.athleticaapp.api.client.AuthTokenManager
import com.example.athleticaapp.repositories.AuthRepository
import com.example.athleticaapp.session.UserSession
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var btnGoogle: Button
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView
    private lateinit var tvForgotPassword: TextView

    private val authRepository by lazy { AuthRepository(this) }

    companion object {
        private const val TAG = "LoginActivity"
    }

    // 🔹 Lanzador del Intent de Google (como antes)
    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(Exception::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: Exception) {
                Log.e(TAG, "Error al obtener cuenta de Google", e)
                Toast.makeText(
                    this,
                    "Error al iniciar con Google: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar FirebaseAuth
        auth = FirebaseAuth.getInstance()

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegister = findViewById(R.id.tvRegister)
        tvForgotPassword = findViewById(R.id.tvForgotPassword)
        btnGoogle = findViewById(R.id.btnGoogle)

        // 🔹 Configurar Google Sign In (mismo flujo de antes, solo que usando el clientId del json)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Botones
        btnLogin.setOnClickListener { loginUser() }

        btnGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, RecoverPasswordActivity::class.java))
        }
    }

    // 🔹 Autenticación con Firebase usando cuenta de Google (COMO ANTES)
    // No se toca tu API aquí, solo Firebase → MainActivity
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        if (account == null) return

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        btnGoogle.isEnabled = false

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                btnGoogle.isEnabled = true

                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val displayName = user?.displayName ?: "Usuario"

                    Toast.makeText(
                        this,
                        "Bienvenido $displayName",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Como ahora MainActivity usa ROLE_NAME para el menú,
                    // le mandamos un rol por defecto "USER"
                    val intent = Intent(this, MainActivity::class.java).apply {
                        putExtra("ROLE_NAME", "USER")
                    }
                    startActivity(intent)
                    finish()
                } else {
                    Log.e(TAG, "Error de autenticación con Google", task.exception)
                    Toast.makeText(
                        this,
                        "Error de autenticación con Google",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    // 🔹 Login tradicional con backend (esto se queda como LO TENÍAS FUNCIONANDO)
    private fun loginUser() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Ingrese un correo válido", Toast.LENGTH_SHORT).show()
            return
        }

        btnLogin.isEnabled = false

        lifecycleScope.launch {
            try {
                Log.d(TAG, "Login normal: intentando login en backend...")

                val response = authRepository.login(email, password)

                val nombre = response.data.user.name
                val roleName = response.data.user.role.name
                val userId = response.data.user.id

                UserSession.saveUserId(this@LoginActivity, userId)

                Log.d(TAG, "Login normal OK. Rol: $roleName")

                Toast.makeText(
                    this@LoginActivity,
                    "Bienvenido $nombre",
                    Toast.LENGTH_LONG
                ).show()
                AuthTokenManager.saveToken(this@LoginActivity, response.data.token)

                val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
                    putExtra("ROLE_NAME", roleName)
                }
                startActivity(intent)
                finish()

            } catch (e: Exception) {
                Log.e(TAG, "Error en login normal", e)
                Toast.makeText(
                    this@LoginActivity,
                    "Error al iniciar sesión: ${e.message ?: "Intenta de nuevo"}",
                    Toast.LENGTH_LONG
                ).show()
            } finally {
                btnLogin.isEnabled = true
            }
        }
    }
}
