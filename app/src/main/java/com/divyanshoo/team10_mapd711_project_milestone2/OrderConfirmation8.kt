//OrderConfirmation8.kt
package com.divyanshoo.team10_mapd711_project_milestone2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OrderConfirmation8 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirmation8)

        // Retrieving data passed via Intent
        val phoneBrand = intent.getStringExtra("SELECTED_BRAND")
        val phoneModel = intent.getStringExtra("phoneModel")
        val phonePrice = intent.getStringExtra("phonePrice")
        val phoneStorage = intent.getStringExtra("phoneStorage")
        val phoneColor = intent.getStringExtra("phoneColor")
        val customerName = intent.getStringExtra("customerName")
        val deliveryAddress = intent.getStringExtra("deliveryAddress")

        // Initializing the TextViews to display the order information
        val orderSummaryTextView: TextView = findViewById(R.id.orderSummaryTextView)
        val customerInfoTextView: TextView = findViewById(R.id.customerInfoTextView)

        // Displaying the order summary
        orderSummaryTextView.text = """
            Phone Brand: $phoneBrand
            Model: $phoneModel
            Price: $phonePrice
            Storage: $phoneStorage
            Color: $phoneColor
        """.trimIndent()

        // Displaying customer info
        customerInfoTextView.text = """
            Customer Name: $customerName
            Delivery Address: $deliveryAddress
        """.trimIndent()

        //adding return to home page functionality
        val homeButton: Button = findViewById(R.id.homeButton)
        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
