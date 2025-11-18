package com.example.athleticaapp.repositories

import com.example.athleticaapp.User
import com.example.athleticaapp.UserRole

object UserRepository {
    private val users = mutableListOf<User>()
    var currentUser: User? = null

    fun addUser(user: User) {
        users.add(user)
    }

    fun getAllUsers(): List<User> = users

    fun login(email: String, password: String): User? {
        val user = users.find { it.email == email && it.password == password }
        currentUser = user
        return user
    }

    fun logout() {
        currentUser = null
    }

    fun updateUser(updatedUser: User) {
        val index = users.indexOfFirst { it.id == updatedUser.id }
        if (index != -1) {
            users[index] = updatedUser
        }
    }
}
