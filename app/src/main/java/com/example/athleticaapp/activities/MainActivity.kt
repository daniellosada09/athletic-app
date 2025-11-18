package com.example.athleticaapp.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.athleticaapp.R
import com.example.athleticaapp.fragments.AddressListFragment
import com.example.athleticaapp.fragments.AdminProductFragment
import com.example.athleticaapp.fragments.CartFragment
import com.example.athleticaapp.fragments.HomeFragment
import com.example.athleticaapp.fragments.OrderHistoryFragment
import com.example.athleticaapp.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    private var userRole: String = "user" // valor por defecto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottomNavigation)

        // Obtener el rol desde el Intent enviado por LoginActivity
        val roleFromIntent = intent.getStringExtra("ROLE_NAME") ?: "USER"
        userRole = roleFromIntent.lowercase()

        Log.d("MainActivity", "Rol recibido: $roleFromIntent (normalizado: $userRole)")

        // Configurar el menú según el rol
        setupMenuForRole()

        // Fragmento inicial
        loadFragment(HomeFragment())

        // Navegación inferior
        bottomNavigation.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.menu_inicio -> HomeFragment()
                R.id.menu_cart -> CartFragment()
                R.id.menu_addresses -> AddressListFragment()
                R.id.menu_profile -> ProfileFragment()
                R.id.menu_admin_product -> AdminProductFragment()
                else -> HomeFragment()
            }
            loadFragment(fragment)
            true
        }

    }

    private fun setupMenuForRole() {
        val menu = bottomNavigation.menu
        menu.clear()

        menu.add(0, R.id.menu_inicio, 0, "Inicio")
            .setIcon(android.R.drawable.ic_menu_view)

        menu.add(0, R.id.menu_cart, 1, "Carrito")
            .setIcon(android.R.drawable.ic_menu_add)

        menu.add(0, R.id.menu_addresses, 2, "Direcciones")

        menu.add(0, R.id.menu_profile, 4, "Perfil")
            .setIcon(android.R.drawable.ic_menu_myplaces)

        if (userRole == "admin") {
            menu.add(0, R.id.menu_admin_product, 5, "Productos")
                .setIcon(android.R.drawable.ic_menu_manage)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
