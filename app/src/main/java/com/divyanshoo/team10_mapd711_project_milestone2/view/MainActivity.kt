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
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.divyanshoo.team10_mapd711_project_milestone2.R
import com.divyanshoo.team10_mapd711_project_milestone2.model.AppDatabase
import com.divyanshoo.team10_mapd711_project_milestone2.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize userRepository
        val userDao = AppDatabase.getDatabase(this).userDao()
        userRepository = UserRepository(userDao)

//        // Check if user is already logged in
       val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
//        val loggedInUserEmail = sharedPreferences.getString("userEmail", null)
//
//        // If user is logged in, skip login screen and navigate directly to the next activity
//        if (loggedInUserEmail != null) {
//            val intent = Intent(this@MainActivity, UserDetailsActivity9::class.java)
//            intent.putExtra("USER_EMAIL", loggedInUserEmail)
//            startActivity(intent)
//            finish() // Close the login screen
//            return
//        }

        val emailField = findViewById<EditText>(R.id.et_email)
        val passwordField = findViewById<EditText>(R.id.et_password)
        val loginButton = findViewById<Button>(R.id.btn_login)
        val registerButton = findViewById<Button>(R.id.btn_register)

        // Login button logic
        // Inside your MainActivity.kt

        loginButton.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    val user = userRepository.getUserByEmail(email)
                    if (user != null && user.password == password) {
                        // Save the logged-in user's information in SharedPreferences
                        val editor = sharedPreferences.edit()
                        editor.putString("userEmail", email)
                        editor.putString("firstName", user.firstname)
                        editor.putString("lastName", user.lastname)
                        editor.putInt("LOGGED_IN_USER_ID", user.custId)  // Store the user's ID
                        editor.apply()  // Don't forget to apply the changes!

                        // Navigate to the UserDetailsActivity
                        runOnUiThread {
                            val intent = Intent(this@MainActivity, UserDetailsActivity9::class.java)
                            intent.putExtra("USER_EMAIL", email)
                            startActivity(intent)
                            finish() // Close the login screen
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        // Register button logic
        registerButton.setOnClickListener {
            // Navigate to RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
