//DeliverySelectionActivity5a.kt

package com.divyanshoo.team10_mapd711_project_milestone2

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class DeliverySelectionActivity5a : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_selection_activity5a)

        // Retrieve phone details from intent
        val selectedBrand = intent.getStringExtra("SELECTED_BRAND") ?: "N/A"
        val selectedModel = intent.getStringExtra("SELECTED_MODEL") ?: "N/A"
        val selectedPrice = intent.getStringExtra("SELECTED_PRICE") ?: "N/A"
        val selectedStorage = intent.getStringExtra("SELECTED_STORAGE") ?: "N/A"
        val selectedColor = intent.getStringExtra("SELECTED_COLOR") ?: "N/A"

        // Setup Spinner for delivery type
        val deliveryOptions = arrayOf("Door Delivery", "Campus Pickup")
        val deliverySpinner: Spinner = findViewById(R.id.deliverySpinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, deliveryOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        deliverySpinner.adapter = adapter

        // Setup Button to proceed
        val proceedButton: Button = findViewById(R.id.proceedButton)
        proceedButton.setOnClickListener {
            val selectedDeliveryOption = deliverySpinner.selectedItem.toString()

            if (selectedDeliveryOption == "Campus Pickup") {
                // Navigate to MapActivity for campus selection
                val intent = Intent(this, MapActivity5b::class.java)
                // Pass phone details and selected delivery type
                intent.putExtra("SELECTED_BRAND", selectedBrand)
                intent.putExtra("SELECTED_MODEL", selectedModel)
                intent.putExtra("SELECTED_PRICE", selectedPrice)
                intent.putExtra("SELECTED_STORAGE", selectedStorage)
                intent.putExtra("SELECTED_COLOR", selectedColor)
                intent.putExtra("DELIVERY_TYPE", selectedDeliveryOption)
                startActivity(intent)
            } else {
                // Navigate to CustomerInfoActivity for door delivery
                val intent = Intent(this, CustomerInfoActivity6::class.java)
                // Pass phone details and selected delivery type
                intent.putExtra("SELECTED_BRAND", selectedBrand)
                intent.putExtra("SELECTED_MODEL", selectedModel)
                intent.putExtra("SELECTED_PRICE", selectedPrice)
                intent.putExtra("SELECTED_STORAGE", selectedStorage)
                intent.putExtra("SELECTED_COLOR", selectedColor)
                intent.putExtra("DELIVERY_TYPE", selectedDeliveryOption)
                startActivity(intent)
            }
        }
    }
}
