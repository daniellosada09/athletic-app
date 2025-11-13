package com.example.athleticaapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.athleticaapp.ProductAdapter
import com.example.athleticaapp.repositories.ProductRepository
import com.example.athleticaapp.R
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val productRepository by lazy { ProductRepository(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // ✔ CORRECTO: usar viewLifecycleOwner.lifecycleScope
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                Log.d("HOME", "🟦 Cargando productos...")

                val products = productRepository.fetchAllProducts()

                Log.d("HOME", "🟩 Productos recibidos: $products")

                recyclerView.adapter = ProductAdapter(products)

            } catch (e: Exception) {
                Log.e("HOME", "❌ Error cargando productos", e)
                Toast.makeText(requireContext(), "Error cargando productos", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
