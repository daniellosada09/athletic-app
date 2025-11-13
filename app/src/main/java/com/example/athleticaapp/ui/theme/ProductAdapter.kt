package com.example.athleticaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.athleticaapp.api.dto.product.ProductDto

class ProductAdapter(
    private val productList: List<ProductDto>,
    private val onAddToCart: ((ProductDto) -> Unit)? = null
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProductImage: ImageView = itemView.findViewById(R.id.ivProductImage)
        val tvProductName: TextView = itemView.findViewById(R.id.tvProductName)
        val tvProductPrice: TextView = itemView.findViewById(R.id.tvProductPrice)
        val btnAddToCart: Button? = itemView.findViewById(R.id.btnAddToCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        holder.tvProductName.text = product.title
        holder.tvProductPrice.text = "$${String.format("%,.0f", product.price)}"

        // Como el backend envía URL, mostramos placeholder
        holder.ivProductImage.setImageResource(R.mipmap.ic_launcher)

        // Si el botón existe (solo en el Home)
        holder.btnAddToCart?.setOnClickListener {
            onAddToCart?.invoke(product)
            Toast.makeText(holder.itemView.context, "${product.title} agregado al carrito", Toast.LENGTH_SHORT).show()
        }
    }
}
