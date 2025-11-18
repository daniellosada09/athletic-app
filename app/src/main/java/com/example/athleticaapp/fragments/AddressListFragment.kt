package com.example.athleticaapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.athleticaapp.AddressesAdapter
import com.example.athleticaapp.R
import com.example.athleticaapp.repositories.AddressRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddressListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAddAddress: Button
    private val addressRepository by lazy { AddressRepository(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_address_list, container, false)

        recyclerView = view.findViewById(R.id.recyclerAddresses)
        btnAddAddress = view.findViewById(R.id.btnAddAddress)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        btnAddAddress.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, AddressFormFragment())
                .addToBackStack(null)
                .commit()
        }

        loadAddresses()
        return view
    }

    private fun loadAddresses() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val addresses = addressRepository.getMyAddresses()
                withContext(Dispatchers.Main) {
                    recyclerView.adapter = AddressesAdapter(addresses)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
