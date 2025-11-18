package com.example.athleticaapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.athleticaapp.ProductAdapter
import com.example.athleticaapp.repositories.ProductRepository
import com.example.athleticaapp.R
import com.example.athleticaapp.CartManager
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val productRepository by lazy { ProductRepository(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerHome)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // 🔥 NUEVO — Referencia al botón
        val btnMisPedidos = view.findViewById<Button>(R.id.btnMisPedidos)
        btnMisPedidos.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, OrderHistoryFragment())
                .addToBackStack(null)
                .commit()
        }

        // 🔥 Carga de productos
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val products = productRepository.fetchAllProducts()

                recyclerView.adapter = ProductAdapter(products) { product ->
                    CartManager.addToCart(requireContext(), product)
                    Toast.makeText(
                        requireContext(),
                        "${product.title} agregado al carrito",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error cargando productos", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
