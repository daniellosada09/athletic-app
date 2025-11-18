package com.example.athleticaapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.athleticaapp.R
import com.example.athleticaapp.api.dto.addresses.CreateAddressesRequest
import com.example.athleticaapp.repositories.AddressRepository
import com.example.athleticaapp.session.UserSession
import kotlinx.coroutines.launch

class AddressFormFragment : Fragment() {

    private lateinit var repo: AddressRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_address_form, container, false)

        repo = AddressRepository(requireContext())

        val etLabel = view.findViewById<EditText>(R.id.etLabel)
        val etAddress1 = view.findViewById<EditText>(R.id.etAddress1)
        val etAddress2 = view.findViewById<EditText>(R.id.etAddress2)
        val etCityId = view.findViewById<EditText>(R.id.etCityId)
        val etState = view.findViewById<EditText>(R.id.etState)
        val etPostalCode = view.findViewById<EditText>(R.id.etPostalCode)
        val etPhone = view.findViewById<EditText>(R.id.etPhone)
        val btnSave = view.findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {

            // ✔ Obtener el ID del usuario desde sesión
            val userId = UserSession.getUserId(requireContext())

            if (userId == null) {
                Toast.makeText(requireContext(), "Error: usuario no autenticado", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // ✔ Validar campos mínimos
            if (etLabel.text.isBlank() || etAddress1.text.isBlank() || etCityId.text.isBlank()) {
                Toast.makeText(requireContext(), "Completa los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val body = CreateAddressesRequest(
                userId = userId,
                label = etLabel.text.toString(),
                addressLine1 = etAddress1.text.toString(),
                addressLine2 = etAddress2.text.toString(),
                cityId = etCityId.text.toString(),
                state = etState.text.toString(),
                postalCode = etPostalCode.text.toString(),
                phone = etPhone.text.toString(),
            )

            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    repo.createAddress(body)

                    Toast.makeText(requireContext(), "Dirección guardada!", Toast.LENGTH_SHORT).show()

                    // ✔ Volver atrás
                    parentFragmentManager.popBackStack()

                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Error guardando dirección", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return view
    }
}
