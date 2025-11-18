package com.example.athleticaapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.athleticaapp.api.dto.addresses.AddressesDto
import com.example.athleticaapp.fragments.AddressFormFragment

class AddressesAdapter(
    private val addresses: List<AddressesDto>
) : RecyclerView.Adapter<AddressesAdapter.AddressViewHolder>() {

    inner class AddressViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvLabel: TextView = view.findViewById(R.id.tvAddressLabel)
        val tvLine: TextView = view.findViewById(R.id.tvAddressLine)
        val tvCity: TextView = view.findViewById(R.id.tvCity)
        val tvPhone: TextView = view.findViewById(R.id.tvPhone)
        val btnEdit: Button = view.findViewById(R.id.btnEditAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_address, parent, false)
        return AddressViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val address = addresses[position]

        holder.tvLabel.text = address.label
        holder.tvLine.text = "${address.addressLine1} ${address.addressLine2}"
        holder.tvCity.text = "${address.cityName}, ${address.state}"
        holder.tvPhone.text = "Tel: ${address.phone}"

        // abrir fragmento con datos de la dirección para editar
        holder.btnEdit.setOnClickListener {
            val activity = holder.itemView.context as androidx.appcompat.app.AppCompatActivity

            val fragment = AddressFormFragment().apply {
                arguments = Bundle().apply {
                    putString("addressId", address.id)
                    putString("label", address.label)
                    putString("line1", address.addressLine1)
                    putString("line2", address.addressLine2)
                    putString("cityId", address.cityId)
                    putString("cityName", address.cityName)
                    putString("state", address.state)
                    putString("postalCode", address.postalCode)
                    putString("phone", address.phone)
                }
            }

            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int = addresses.size
}
