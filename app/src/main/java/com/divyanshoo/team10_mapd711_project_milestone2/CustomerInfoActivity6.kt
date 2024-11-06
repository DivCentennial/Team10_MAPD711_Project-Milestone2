//CustomerInfoActivity6.kt
package com.divyanshoo.team10_mapd711_project_milestone2

import android.os.Bundle
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CustomerInfoActivity6 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_info6)

        // Retrieve selected phone details from the Intent (if passed)
        val selectedBrand = intent.getStringExtra("selectedBrand")
        val selectedModel = intent.getStringExtra("selectedModel")
        val selectedStorage = intent.getStringExtra("selectedStorage")
        val selectedColor = intent.getStringExtra("selectedColor")

        // Initialize EditTexts for user input
        val fullNameEditText: EditText = findViewById(R.id.fullNameEditText)
        val addressEditText: EditText = findViewById(R.id.addressEditText)
        val phoneEditText: EditText = findViewById(R.id.phoneEditText)
        val emailEditText: EditText = findViewById(R.id.emailEditText)

        // Set up the "Submit" button to capture the input data
        val submitButton: Button = findViewById(R.id.submitButton)
        submitButton.setOnClickListener {
            // Capture the user inputs
            val fullName = fullNameEditText.text.toString()
            val address = addressEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val email = emailEditText.text.toString()

            // Validate the user input
            if (fullName.isNotEmpty() && address.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty()) {
                // Proceed with the order and show a confirmation message
                Toast.makeText(this, "Order confirmed for $fullName!", Toast.LENGTH_SHORT).show()

                // Optionally, you can pass all the collected data to another activity (e.g., confirmation screen)
            } else {
                // Show a toast message if any field is empty
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
