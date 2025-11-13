package com.example.athleticaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.athleticaapp.api.dto.product.ProductDto

class AdminProductAdapter(
    private var products: MutableList<ProductDto>,
    private val onActionClick: (ProductDto, String) -> Unit
) : RecyclerView.Adapter<AdminProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgProduct: ImageView = view.findViewById(R.id.imgProduct)
        val txtName: TextView = view.findViewById(R.id.txtProductName)
        val txtPrice: TextView = view.findViewById(R.id.txtProductPrice)
        val txtDescription: TextView = view.findViewById(R.id.txtProductDescription)
        val txtStock: TextView = view.findViewById(R.id.txtProductStock)
        val btnEdit: Button = view.findViewById(R.id.btnEdit)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val p = products[position]

        holder.imgProduct.setImageResource(R.mipmap.ic_launcher)

        holder.txtName.text = p.title
        holder.txtPrice.text = "$${String.format("%,.2f", p.price)}"
        holder.txtDescription.text = p.description
        holder.txtStock.text = "Stock: ${p.stock}"

        holder.btnEdit.setOnClickListener { onActionClick(p, "edit") }
        holder.btnDelete.setOnClickListener { onActionClick(p, "delete") }
    }

    fun updateList(newList: List<ProductDto>) {
        products.clear()
        products.addAll(newList)
        notifyDataSetChanged()
    }
}
