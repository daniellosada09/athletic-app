package com.example.athleticaapp
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class CartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        val btnCheckout: Button = view.findViewById(R.id.btnCheckout)

        btnCheckout.setOnClickListener {
            Toast.makeText(requireContext(), "Procediendo al pago...", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
