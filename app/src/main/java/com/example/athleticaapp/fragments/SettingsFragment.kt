package com.example.athleticaapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.athleticaapp.R

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val switchNotifications: Switch = view.findViewById(R.id.switchNotifications)
        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            val msg = if (isChecked)
                getString(R.string.notificaciones_activadas)
            else
                getString(R.string.notificaciones_desactivadas)
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        }

        return view
    }
}