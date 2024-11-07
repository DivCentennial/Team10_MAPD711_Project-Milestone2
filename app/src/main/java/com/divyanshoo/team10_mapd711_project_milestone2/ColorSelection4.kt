//color_selection4.kt

// View description: User can select desired color.
package com.divyanshoo.team10_mapd711_project_milestone2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge

class ColorSelection4 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_color_selection4)

        // Configure the edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize the color spinner
        val colorSpinner: Spinner = findViewById(R.id.colorSpinner)
        val colorOptions = listOf("Blue 💙", "Black 🖤", "Silver 🩶", "Gold 💛", "Red ❤️")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, colorOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        colorSpinner.adapter = adapter

        // Set "Blue 💙" as the default selected item
        colorSpinner.setSelection(0)

        // Gets the selected phone details from the previous screen via Intent
        val selectedBrand = intent.getStringExtra("SELECTED_BRAND")
        val selectedModel = intent.getStringExtra("SELECTED_MODEL")
        val selectedPrice = intent.getStringExtra("SELECTED_PRICE")
        val selectedStorage = intent.getStringExtra("SELECTED_STORAGE")

        // Log the received data for debugging
        Log.d("ColorSelection4", "Selected Brand: $selectedBrand")
        Log.d("ColorSelection4", "Selected Model: $selectedModel")
        Log.d("ColorSelection4", "Selected Storage: $selectedStorage")
        Log.d("ColorSelection4", "Selected Price: $selectedPrice")

        // Set up the "Next" button to pass the data to CheckoutActivity
        val nextButton: Button = findViewById(R.id.checkoutButton)
        nextButton.setOnClickListener {
            // Get selected color from the spinner
            val selectedColor = colorSpinner.selectedItem.toString()

            // Log the selected color for debugging
            Log.d("ColorSelection4", "Selected Color: $selectedColor")

            // Validate that a color is selected
            if (selectedColor.isEmpty()) {
                Toast.makeText(this, "Please select a color", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create an Intent to pass data to CheckoutActivity
            val intent = Intent(this, CheckoutActivity5::class.java)

            // Pass all selected data via the Intent
            intent.putExtra("SELECTED_BRAND", selectedBrand)
            intent.putExtra("SELECTED_MODEL", selectedModel)
            intent.putExtra("SELECTED_PRICE", selectedPrice)
            intent.putExtra("SELECTED_STORAGE", selectedStorage)
            intent.putExtra("SELECTED_COLOR", selectedColor)

            // Start the CheckoutActivity
            startActivity(intent)
        }
    }
}
