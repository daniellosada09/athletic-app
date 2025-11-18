package com.example.athleticaapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.athleticaapp.R
import com.example.athleticaapp.adapters.OrderHistoryAdapter
import com.example.athleticaapp.repositories.OrderRepository
import kotlinx.coroutines.launch

class OrderHistoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private val repo by lazy { OrderRepository(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_order_history, container, false)

        recyclerView = view.findViewById(R.id.recyclerOrders)
        progressBar = view.findViewById(R.id.progressOrders)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        loadOrders()

        return view
    }

    private fun loadOrders() {
        progressBar.visibility = View.VISIBLE

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val orderList = repo.getMyOrders() // YA devuelve la lista en .data

                recyclerView.adapter = OrderHistoryAdapter(orderList)

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error cargando órdenes", Toast.LENGTH_LONG).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
}
