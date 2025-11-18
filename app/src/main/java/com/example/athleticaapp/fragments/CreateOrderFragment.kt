package com.example.athleticaapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.athleticaapp.CartManager
import com.example.athleticaapp.R
import com.example.athleticaapp.api.dto.order.CreateOrderRequest
import com.example.athleticaapp.api.dto.order.OrderItemRequest
import com.example.athleticaapp.repositories.AddressRepository
import com.example.athleticaapp.repositories.OrderRepository
import com.example.athleticaapp.repositories.UserRepository
import com.example.athleticaapp.session.UserSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateOrderFragment : Fragment() {

    private lateinit var spinnerAddress: Spinner
    private lateinit var btnConfirm: Button
    private val addressRepository by lazy { AddressRepository(requireContext()) }

    private val orderRepository by lazy { OrderRepository(requireContext()) }

    private var addresses = listOf<com.example.athleticaapp.api.dto.addresses.AddressesDto>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_create_order, container, false)

        spinnerAddress = view.findViewById(R.id.spinnerAddress)
        btnConfirm = view.findViewById(R.id.btnConfirmOrder)

        loadAddresses()

        btnConfirm.setOnClickListener {
            createOrder()
        }

        return view
    }

    private fun loadAddresses() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                addresses = addressRepository.getMyAddresses()

                withContext(Dispatchers.Main) {
                    val labels = addresses.map { it.label }
                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, labels)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerAddress.adapter = adapter
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun createOrder() {
        val selected = spinnerAddress.selectedItemPosition
        if (selected == -1) {
            Toast.makeText(requireContext(), "Selecciona una dirección", Toast.LENGTH_SHORT).show()
            return
        }

        val addressId = addresses[selected].id
        val userId = UserSession.getUserId(requireContext())

        if (userId == null) {
            Toast.makeText(requireContext(), "Error: usuario no autenticado", Toast.LENGTH_LONG).show()
            return
        }

        val cartItems = CartManager.getCart(requireContext())

        val items = cartItems.map {
            OrderItemRequest(
                productId = it.id,
                quantity = 1
            )
        }

        val body = CreateOrderRequest(
            userId = userId,
            addressId = addressId,
            items = items,
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                orderRepository.createOrder(body)

                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Orden creada exitosamente", Toast.LENGTH_LONG).show()

                    CartManager.clearCart(requireContext())

                    parentFragmentManager.popBackStack()
                }

            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error al crear la orden", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}
