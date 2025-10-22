package com.example.athleticaapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerCart)
        val tvTotal = view.findViewById<TextView>(R.id.tvTotal)
        val btnClear = view.findViewById<Button>(R.id.btnClearCart)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Cargar productos del carrito
        val cartItems = CartRepository.getCart()
        recyclerView.adapter = ProductAdapter(cartItems)

        // Mostrar total
        val total = CartRepository.getTotal()
        tvTotal.text = "Total: $${String.format("%,.0f", total)}"

        btnClear.setOnClickListener {
            CartRepository.clearCart()
            recyclerView.adapter = ProductAdapter(emptyList())
            tvTotal.text = "Total: $0"
        }

        return view
    }
}
