//CheckoutActivity5.kt
package com.divyanshoo.team10_mapd711_project_milestone2

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class CheckoutActivity5 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout5)

        // Retrieve selected phone details from the intent
        val selectedBrand = intent.getStringExtra("selectedBrand") ?: "N/A"
        val selectedModel = intent.getStringExtra("selectedModel") ?: "N/A"
        val selectedStorage = intent.getStringExtra("selectedStorage") ?: "N/A"
        val selectedColor = intent.getStringExtra("selectedColor") ?: "N/A"

        // Set values to TextViews
        findViewById<TextView>(R.id.brandTextView).text = "Brand: $selectedBrand"
        findViewById<TextView>(R.id.modelTextView).text = "Model: $selectedModel"
        findViewById<TextView>(R.id.storageTextView).text = "Storage: $selectedStorage"
        findViewById<TextView>(R.id.colorTextView).text = "Color: $selectedColor"

        // Set up the confirm button to proceed to Customer Information screen
        val confirmButton: Button = findViewById(R.id.confirmButton)
        confirmButton.setOnClickListener {
            // Navigate to Customer Information Activity
            val intent = Intent(this, CustomerInfoActivity6::class.java)
            startActivity(intent)
        }
    }
}
