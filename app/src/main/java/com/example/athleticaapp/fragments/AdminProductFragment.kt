package com.example.athleticaapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.athleticaapp.AdminProductAdapter
import com.example.athleticaapp.R
import com.example.athleticaapp.api.dto.product.ProductDto
import com.example.athleticaapp.repositories.ProductRepository
import kotlinx.coroutines.launch

class AdminProductFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAdd: Button
    private lateinit var etTitle: EditText
    private lateinit var etPrice: EditText
    private lateinit var etDescription: EditText
    private lateinit var etStock: EditText
    private lateinit var etCategoryId: EditText

    private lateinit var adapter: AdminProductAdapter
    private lateinit var repository: ProductRepository

    private val productList = mutableListOf<ProductDto>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val v = inflater.inflate(R.layout.fragment_admin_product, container, false)

        repository = ProductRepository(requireContext())

        recyclerView = v.findViewById(R.id.recyclerAdminProducts)
        btnAdd = v.findViewById(R.id.btnAddProduct)
        etTitle = v.findViewById(R.id.etProductName)
        etPrice = v.findViewById(R.id.etProductPrice)
        etDescription = v.findViewById(R.id.etProductDescription)
        etStock = v.findViewById(R.id.etProductStock)
        etCategoryId = v.findViewById(R.id.etCategoryId)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.isNestedScrollingEnabled = false  // 🔥 Lo importante
        recyclerView.overScrollMode = View.OVER_SCROLL_NEVER

        adapter = AdminProductAdapter(
            products = productList,
            onActionClick = { product, action ->
                when (action) {
                    "delete" -> deleteProduct(product)
                    "edit" -> Toast.makeText(requireContext(), "Función de edición no disponible", Toast.LENGTH_SHORT).show()
                }
            }
        )

        recyclerView.adapter = adapter

        btnAdd.setOnClickListener { createProduct() }

        loadProducts()

        return v
    }

    private fun loadProducts() {
        lifecycleScope.launch {
            try {
                val result = repository.fetchAllProducts()
                productList.clear()
                productList.addAll(result)

                adapter.updateList(productList)

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error cargando productos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createProduct() {
        val title = etTitle.text.toString().trim()
        val price = etPrice.text.toString().trim().toDoubleOrNull()
        val description = etDescription.text.toString().trim()
        val stock = etStock.text.toString().trim().toIntOrNull()
        val categoryId = etCategoryId.text.toString().trim()

        if (title.isEmpty() || price == null || description.isEmpty() || stock == null || categoryId.isEmpty()) {
            Toast.makeText(requireContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val newProduct = repository.createProduct(
                    title = title,
                    description = description,
                    price = price,
                    stock = stock,
                    categoryId = categoryId,
                    image = ""
                )

                productList.add(newProduct)
                adapter.updateList(productList)

                etTitle.text.clear()
                etPrice.text.clear()
                etDescription.text.clear()
                etStock.text.clear()
                etCategoryId.text.clear()

                Toast.makeText(requireContext(), "Producto creado correctamente", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error al crear producto: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun deleteProduct(product: ProductDto) {
        lifecycleScope.launch {
            try {
                repository.deleteProduct(product.id)
                productList.remove(product)
                adapter.updateList(productList)

                Toast.makeText(requireContext(), "Producto eliminado", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error eliminando producto", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
