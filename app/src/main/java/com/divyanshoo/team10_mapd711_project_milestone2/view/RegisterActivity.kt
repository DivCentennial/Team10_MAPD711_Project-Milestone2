package com.divyanshoo.team10_mapd711_project_milestone2.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.divyanshoo.team10_mapd711_project_milestone2.model.AppDatabase
import com.divyanshoo.team10_mapd711_project_milestone2.R
import com.divyanshoo.team10_mapd711_project_milestone2.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase // Database instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Room database
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()

        // Find views for email, password, confirm password, and the new fields
        val emailField = findViewById<EditText>(R.id.et_email)
        val passwordField = findViewById<EditText>(R.id.et_password)
        val confirmPasswordField = findViewById<EditText>(R.id.et_confirm_password)
        val firstNameField = findViewById<EditText>(R.id.et_first_name)  // New field
        val lastNameField = findViewById<EditText>(R.id.et_last_name)    // New field
        val addressField = findViewById<EditText>(R.id.et_address)       // New field
        val cityField = findViewById<EditText>(R.id.et_city)             // New field
        val postalCodeField = findViewById<EditText>(R.id.et_postal_code) // New field
        val registerButton = findViewById<Button>(R.id.btn_register)

        // Handle Register button click
        registerButton.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()
            val confirmPassword = confirmPasswordField.text.toString()
            val firstName = firstNameField.text.toString()
            val lastName = lastNameField.text.toString()
            val address = addressField.text.toString()
            val city = cityField.text.toString()
            val postalCode = postalCodeField.text.toString()

            // Validation
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || firstName.isEmpty() ||
                lastName.isEmpty() || address.isEmpty() || city.isEmpty() || postalCode.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                // Save user to database
                CoroutineScope(Dispatchers.IO).launch {
                    val existingUser = database.userDao().getUser(email, password) // Optional: check for duplicate email
                    if (existingUser == null) {
                        // Create a new user with all required fields
                        val user = User(
                            email = email,
                            password = password,
                            firstname = firstName,
                            lastname = lastName,
                            address = address,
                            city = city,
                            postalCode = postalCode
                        )

                        database.userDao().insertOrUpdateUser(user)

                        runOnUiThread {
                            Toast.makeText(this@RegisterActivity, "Registration successful!", Toast.LENGTH_SHORT).show()
                            finish() // Return to login screen
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@RegisterActivity, "Email already exists", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}
