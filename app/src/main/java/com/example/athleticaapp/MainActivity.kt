package com.example.athleticaapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    private var userRole: String = "user"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottomNavigation)

        // Obtener el rol directamente desde UserRepository
        val currentUser = UserRepository.currentUser
        userRole = currentUser?.role?.name?.lowercase() ?: "user"

        // Configurar el menú según el rol
        setupMenuForRole()

        // Fragmento inicial
        loadFragment(HomeFragment())

        // Navegación inferior
        bottomNavigation.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.menu_inicio -> HomeFragment()
                R.id.menu_cart -> CartFragment()
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

        // Menú base
        menu.add(0, R.id.menu_inicio, 0, "Inicio")
            .setIcon(android.R.drawable.ic_menu_view)
        menu.add(0, R.id.menu_cart, 1, "Carrito")
            .setIcon(android.R.drawable.ic_menu_add)
        menu.add(0, R.id.menu_profile, 2, "Perfil")
            .setIcon(android.R.drawable.ic_menu_myplaces)

        // Solo admin
        if (userRole == "admin") {
            menu.add(0, R.id.menu_admin_product, 3, "Productos")
                .setIcon(android.R.drawable.ic_menu_manage)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
