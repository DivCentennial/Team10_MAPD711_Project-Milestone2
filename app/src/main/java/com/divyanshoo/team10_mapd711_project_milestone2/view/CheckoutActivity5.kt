//checkout_activity5.kt
// View description: User can see the selected phone details and confirm the order.


package com.divyanshoo.team10_mapd711_project_milestone2.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.divyanshoo.team10_mapd711_project_milestone2.R
import com.divyanshoo.team10_mapd711_project_milestone2.viewmodel.CheckoutViewModel

class CheckoutActivity5 : AppCompatActivity() {

    private val checkoutViewModel: CheckoutViewModel by viewModels() // Get ViewModel instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout5)

        // Retrieve selected phone details from the intent
        val selectedBrand = intent.getStringExtra("SELECTED_BRAND") ?: "N/A"
        val selectedModel = intent.getStringExtra("SELECTED_MODEL") ?: "N/A"
        val selectedStorage = intent.getStringExtra("SELECTED_STORAGE") ?: "N/A"
        val selectedColor = intent.getStringExtra("SELECTED_COLOR") ?: "N/A"
        val selectedPrice = intent.getStringExtra("SELECTED_PRICE") ?: "N/A"  // Retrieve price

        // Set data in ViewModel
        checkoutViewModel.setSelectedPhoneDetails(selectedBrand, selectedModel, selectedStorage, selectedColor, selectedPrice)

        // Observe the LiveData from ViewModel
        checkoutViewModel.selectedPhoneDetails.observe(this, { details ->
            // Set values to TextViews
            findViewById<TextView>(R.id.brandTextView).text = "Brand: ${details["brand"]}"
            findViewById<TextView>(R.id.modelTextView).text = "Model: ${details["model"]}"
            findViewById<TextView>(R.id.storageTextView).text = "Storage: ${details["storage"]}"
            findViewById<TextView>(R.id.colorTextView).text = "Color: ${details["color"]}"
            findViewById<TextView>(R.id.priceTextView).text = "Price: ${details["price"]}"
        })

        // Set up the confirm button to proceed to Customer Information screen
        val confirmButton: Button = findViewById(R.id.confirmButton)
        confirmButton.setOnClickListener {
            // Navigate to Customer Information Activity
            val intent = Intent(this, DeliverySelectionActivity5a::class.java)

            // Pass all selected data via the Intent
            intent.putExtra("SELECTED_BRAND", selectedBrand)
            intent.putExtra("SELECTED_MODEL", selectedModel)
            intent.putExtra("SELECTED_PRICE", selectedPrice)
            intent.putExtra("SELECTED_STORAGE", selectedStorage)
            intent.putExtra("SELECTED_COLOR", selectedColor)

            startActivity(intent)
        }
    }
}
