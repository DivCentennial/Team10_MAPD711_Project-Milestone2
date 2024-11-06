//CustomerInfoActivity6.kt
package com.divyanshoo.team10_mapd711_project_milestone2

import android.content.Intent
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
        val selectedBrand = intent.getStringExtra("SELECTED_BRAND")
        val selectedModel = intent.getStringExtra("SELECTED_MODEL")
        val selectedStorage = intent.getStringExtra("SELECTED_STORAGE")
        val selectedColor = intent.getStringExtra("SELECTED_COLOR")
        val selectedPrice = intent.getStringExtra("SELECTED_PRICE")

        // Initialize EditTexts for user input
        val fullNameEditText: EditText = findViewById(R.id.fullNameEditText)
        val streetNameEditText: EditText = findViewById(R.id.streetNameEditText)
        val cityEditText: EditText = findViewById(R.id.cityEditText)
        val postalCodeEditText: EditText = findViewById(R.id.postalCodeEditText)
        val phoneEditText: EditText = findViewById(R.id.phoneEditText)
        val emailEditText: EditText = findViewById(R.id.emailEditText)

        // Set up the "Submit" button to capture the input data
        val submitButton: Button = findViewById(R.id.submitButton)
        submitButton.setOnClickListener {
            // Capture the user inputs
            val fullName = fullNameEditText.text.toString()
            val streetName = streetNameEditText.text.toString()
            val city = cityEditText.text.toString()
            val postalCode = postalCodeEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val email = emailEditText.text.toString()

            // Validate the user input
            if (fullName.isNotEmpty() && streetName.isNotEmpty() && city.isNotEmpty() &&
                postalCode.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty()) {

                // Proceed to the PaymentInfoActivity and pass the collected data
                val intent = Intent(this, PaymentInfoActivity7::class.java)
                intent.putExtra("fullName", fullName)
                intent.putExtra("streetName", streetName)
                intent.putExtra("city", city)
                intent.putExtra("postalCode", postalCode)
                intent.putExtra("phone", phone)
                intent.putExtra("email", email)
                intent.putExtra("SELECTED_BRAND", selectedBrand)
                intent.putExtra("SELECTED_MODEL", selectedModel)
                intent.putExtra("SELECTED_STORAGE", selectedStorage)
                intent.putExtra("SELECTED_COLOR", selectedColor)
                intent.putExtra("selectedPrice", selectedPrice)
                startActivity(intent)

            } else {
                // Show a toast message if any field is empty
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
