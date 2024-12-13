/*
Name: - Divyanshoo Sinha (301486627)
      - Kashish Pramod Yadav (301485842)

Milestone 3
Group Project

Team 10

Overall Description: This milestone 3 we integrate google maps in our project from milestone 2 and user can select campus or door delivery.
in campus pickup user can select among 5 locations and based on that the map location is changed.
*/

//MainActivity.kt
// View Description: This view is the opening view with the app logo and a button to order a phone.
package com.divyanshoo.team10_mapd711_project_milestone2.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.divyanshoo.team10_mapd711_project_milestone2.R
import com.divyanshoo.team10_mapd711_project_milestone2.model.AppDatabase
import com.divyanshoo.team10_mapd711_project_milestone2.repository.UserRepository
import com.divyanshoo.team10_mapd711_project_milestone2.viewmodel.MainViewModel
import com.divyanshoo.team10_mapd711_project_milestone2.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    // Use ViewModel with a factory
    private val mainViewModel: MainViewModel by viewModels {
        val userDao = AppDatabase.getDatabase(this).userDao()
        val userRepository = UserRepository(userDao)
        MainViewModelFactory(userRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val emailField = findViewById<EditText>(R.id.et_email)
        val passwordField = findViewById<EditText>(R.id.et_password)
        val loginButton = findViewById<Button>(R.id.btn_login)
        val registerButton = findViewById<Button>(R.id.btn_register)

        // Observe login result
        mainViewModel.loginResult.observe(this, Observer { result ->
            result.onSuccess { user ->
                // Save user info to SharedPreferences
                val editor = sharedPreferences.edit()
                editor.putString("userEmail", user?.email)
                editor.putString("firstName", user?.firstname)
                editor.putString("lastName", user?.lastname)
                editor.putInt("LOGGED_IN_USER_ID", user?.custId ?: -1)
                editor.apply()

                // Navigate to the next activity
                val intent = Intent(this, UserDetailsActivity9::class.java)
                intent.putExtra("USER_EMAIL", user?.email)
                startActivity(intent)
                finish()
            }
            result.onFailure {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        })

        // Login button logic
        loginButton.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                mainViewModel.loginUser(email, password)
            }
        }

        // Register button logic
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
