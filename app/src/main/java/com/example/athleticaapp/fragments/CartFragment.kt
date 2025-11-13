package com.example.athleticaapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.athleticaapp.CartManager
import com.example.athleticaapp.ProductAdapter
import com.example.athleticaapp.R
import com.example.athleticaapp.api.dto.product.ProductDto

class CartFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvTotal: TextView
    private lateinit var btnClear: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        recyclerView = view.findViewById(R.id.recyclerCart)
        tvTotal = view.findViewById(R.id.tvTotal)
        btnClear = view.findViewById(R.id.btnClearCart)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        loadCart()

        btnClear.setOnClickListener {
            CartManager.clearCart(requireContext())
            loadCart()
        }

        return view
    }

    private fun loadCart() {
        val cartItems: List<ProductDto> = CartManager.getCart(requireContext())

        // Usa el ProductAdapter UNIFICADO
        recyclerView.adapter = ProductAdapter(cartItems)

        val total = cartItems.sumOf { it.price }
        tvTotal.text = "Total: $${String.format("%,.0f", total)}"
    }
}
