package com.example.athleticaapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdminProductFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAddProduct: Button
    private lateinit var etProductName: EditText
    private lateinit var etProductPrice: EditText
    private lateinit var etProductDescription: EditText

    private lateinit var adapter: AdminProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin_product, container, false)

        recyclerView = view.findViewById(R.id.recyclerAdminProducts)
        btnAddProduct = view.findViewById(R.id.btnAddProduct)
        etProductName = view.findViewById(R.id.etProductName)
        etProductPrice = view.findViewById(R.id.etProductPrice)
        etProductDescription = view.findViewById(R.id.etProductDescription)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = AdminProductAdapter(ProductRepository.getAllProducts().toMutableList()) { index, action ->
            when (action) {
                "edit" -> {
                    Toast.makeText(requireContext(), "Editar producto aún no implementado", Toast.LENGTH_SHORT).show()
                }
                "delete" -> {
                    ProductRepository.deleteProduct(index)
                    adapter.updateList(ProductRepository.getAllProducts())
                    Toast.makeText(requireContext(), "Producto eliminado", Toast.LENGTH_SHORT).show()
                }
            }
        }
        recyclerView.adapter = adapter

        btnAddProduct.setOnClickListener {
            val name = etProductName.text.toString().trim()
            val priceText = etProductPrice.text.toString().trim()
            val description = etProductDescription.text.toString().trim()

            if (name.isEmpty() || priceText.isEmpty() || description.isEmpty()) {
                Toast.makeText(requireContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val price = priceText.toDoubleOrNull()
            if (price == null || price <= 0) {
                Toast.makeText(requireContext(), "Precio inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newProduct = Product(name, price, R.mipmap.ic_launcher, description)
            ProductRepository.addProduct(newProduct)
            adapter.updateList(ProductRepository.getAllProducts())

            etProductName.text.clear()
            etProductPrice.text.clear()
            etProductDescription.text.clear()

            Toast.makeText(requireContext(), "Producto agregado correctamente", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
