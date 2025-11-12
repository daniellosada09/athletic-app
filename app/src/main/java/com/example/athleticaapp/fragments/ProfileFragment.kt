package com.example.athleticaapp.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.athleticaapp.R
import com.example.athleticaapp.User
import com.example.athleticaapp.repositories.UserRepository

class ProfileFragment : Fragment() {

    private lateinit var ivFotoPerfil: ImageView
    private lateinit var etNombrePerfil: EditText
    private lateinit var etCorreoPerfil: EditText
    private lateinit var etTelefonoPerfil: EditText
    private lateinit var etDireccionPerfil: EditText
    private lateinit var btnEditarPerfil: Button
    private lateinit var btnGuardarPerfil: Button

    private val prefsName = "UserData"
    private var currentUser: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        ivFotoPerfil = view.findViewById(R.id.ivFotoPerfil)
        etNombrePerfil = view.findViewById(R.id.etNombrePerfil)
        etCorreoPerfil = view.findViewById(R.id.etCorreoPerfil)
        etTelefonoPerfil = view.findViewById(R.id.etTelefonoPerfil)
        etDireccionPerfil = view.findViewById(R.id.etDireccionPerfil)
        btnEditarPerfil = view.findViewById(R.id.btnEditarPerfil)
        btnGuardarPerfil = view.findViewById(R.id.btnGuardarPerfil)

        // 🔹 Cargar el usuario logueado desde SharedPreferences
        val prefs = requireActivity().getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        currentUser = UserRepository.getAllUsers().find { it.email == email }

        if (currentUser == null) {
            Toast.makeText(requireContext(), "No se encontró el usuario actual", Toast.LENGTH_SHORT).show()
        } else {
            cargarDatosUsuario(currentUser!!)
        }

        // 🔹 Desactivar campos por defecto
        setEditable(false)

        // 🔹 Editar perfil
        btnEditarPerfil.setOnClickListener {
            setEditable(true)
            Toast.makeText(requireContext(), "Modo edición activado", Toast.LENGTH_SHORT).show()
        }

        // 🔹 Guardar cambios
        btnGuardarPerfil.setOnClickListener {
            guardarCambios()
        }

        return view
    }

    // -------------------------------------------------------
    // 🔹 Habilitar / deshabilitar edición
    private fun setEditable(enabled: Boolean) {
        etNombrePerfil.isEnabled = enabled
        etCorreoPerfil.isEnabled = false // el correo no debe cambiar
        etTelefonoPerfil.isEnabled = enabled
        etDireccionPerfil.isEnabled = enabled
    }

    // -------------------------------------------------------
    // 🔹 Cargar los datos del usuario actual
    private fun cargarDatosUsuario(user: User) {
        etNombrePerfil.setText(user.name)
        etCorreoPerfil.setText(user.email)
        etTelefonoPerfil.setText(user.phone ?: "")
        etDireccionPerfil.setText(user.address ?: "")
    }

    // -------------------------------------------------------
    // 🔹 Guardar los cambios en memoria
    private fun guardarCambios() {
        val user = currentUser ?: return

        val nombre = etNombrePerfil.text.toString().trim()
        val telefono = etTelefonoPerfil.text.toString().trim()
        val direccion = etDireccionPerfil.text.toString().trim()

        if (nombre.isEmpty()) {
            Toast.makeText(requireContext(), "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
            return
        }

        // Confirmación
        AlertDialog.Builder(requireContext())
            .setTitle("Guardar cambios")
            .setMessage("¿Deseas actualizar tu perfil?")
            .setPositiveButton("Sí") { _, _ ->
                val updatedUser = user.copy(
                    name = nombre,
                    phone = telefono,
                    address = direccion
                )

                // Actualiza el usuario en el repositorio
                UserRepository.updateUser(updatedUser)
                currentUser = updatedUser

                // Guarda el correo para mantener la sesión
                val prefs = requireActivity().getSharedPreferences(prefsName, Context.MODE_PRIVATE)
                with(prefs.edit()) {
                    putString("email", updatedUser.email)
                    apply()
                }

                setEditable(false)
                Toast.makeText(requireContext(), "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}