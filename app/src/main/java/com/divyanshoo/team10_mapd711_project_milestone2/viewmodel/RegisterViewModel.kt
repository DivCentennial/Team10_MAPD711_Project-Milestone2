package com.divyanshoo.team10_mapd711_project_milestone2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.divyanshoo.team10_mapd711_project_milestone2.model.User
import com.divyanshoo.team10_mapd711_project_milestone2.repository.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    // Function to register a new user
    fun registerUser(
        email: String,
        password: String,
        confirmPassword: String,
        firstName: String,
        lastName: String,
        address: String,
        city: String,
        postalCode: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ||
            firstName.isEmpty() || lastName.isEmpty() ||
            address.isEmpty() || city.isEmpty() || postalCode.isEmpty()
        ) {
            onError("Please fill in all fields")
        } else if (password != confirmPassword) {
            onError("Passwords do not match")
        } else {
            viewModelScope.launch {
                val existingUser = userRepository.getUserByEmail(email)
                if (existingUser == null) {
                    // Insert user into database
                    val user = User(
                        email = email,
                        password = password,
                        firstname = firstName,
                        lastname = lastName,
                        address = address,
                        city = city,
                        postalCode = postalCode
                    )
                    userRepository.saveUser(user)
                    onSuccess()
                } else {
                    onError("Email already exists")
                }
            }
        }
    }
}
