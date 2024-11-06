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
        val selectedBrand = intent.getStringExtra("SELECTED_BRAND") ?: "N/A"
        val selectedModel = intent.getStringExtra("SELECTED_MODEL") ?: "N/A"
        val selectedStorage = intent.getStringExtra("SELECTED_STORAGE") ?: "N/A"
        val selectedColor = intent.getStringExtra("SELECTED_COLOR") ?: "N/A"
        val selectedPrice = intent.getStringExtra("SELECTED_PRICE") ?: "N/A"  // Retrieve price

        // Set values to TextViews
        findViewById<TextView>(R.id.brandTextView).text = "Brand: $selectedBrand"
        findViewById<TextView>(R.id.modelTextView).text = "Model: $selectedModel"
        findViewById<TextView>(R.id.storageTextView).text = "Storage: $selectedStorage"
        findViewById<TextView>(R.id.colorTextView).text = "Color: $selectedColor"
        findViewById<TextView>(R.id.priceTextView).text = "Price: $selectedPrice"  // Display price

        // Set up the confirm button to proceed to Customer Information screen
        val confirmButton: Button = findViewById(R.id.confirmButton)
        confirmButton.setOnClickListener {
            // Navigate to Customer Information Activity
            val intent = Intent(this, CustomerInfoActivity6::class.java)
            startActivity(intent)
        }
    }
}
