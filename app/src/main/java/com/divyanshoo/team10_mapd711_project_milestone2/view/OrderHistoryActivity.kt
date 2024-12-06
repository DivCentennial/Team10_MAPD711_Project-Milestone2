package com.divyanshoo.team10_mapd711_project_milestone2.view

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.divyanshoo.team10_mapd711_project_milestone2.R

class OrderHistoryActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)

        // Retrieve order details from SharedPreferences
        val orderPhoneBrand = sharedPreferences.getString("ORDER_PHONE_BRAND", "No brand")
        val orderPhoneModel = sharedPreferences.getString("ORDER_PHONE_MODEL", "No model")
        val orderPhonePrice = sharedPreferences.getString("ORDER_PHONE_PRICE", "No price")
        val orderPhoneStorage = sharedPreferences.getString("ORDER_PHONE_STORAGE", "No storage")
        val orderPhoneColor = sharedPreferences.getString("ORDER_PHONE_COLOR", "No color")
        val orderPaymentMethod = sharedPreferences.getString("ORDER_PAYMENT_METHOD", "No payment method")
        val orderDeliveryType = sharedPreferences.getString("ORDER_DELIVERY_TYPE", "No delivery type")
        val orderUserName = sharedPreferences.getString("ORDER_USER_NAME", "No name")
        val orderUserAddress = sharedPreferences.getString("ORDER_USER_ADDRESS", "No address")
        val orderTimestamp = sharedPreferences.getLong("ORDER_TIMESTAMP", 0L)

        // Calculate whether the order can be cancelled (within 24 hours)
        val currentTime = System.currentTimeMillis()
        val canCancelOrder = currentTime - orderTimestamp <= 24 * 60 * 60 * 1000 // 24 hours in milliseconds

        // Display the order details in a TextView
        val orderHistoryTextView: TextView = findViewById(R.id.orderHistoryTextView)
        val orderHistoryText = """
            Order Summary:
            Phone Brand: $orderPhoneBrand
            Phone Model: $orderPhoneModel
            Price: $orderPhonePrice
            Storage: $orderPhoneStorage
            Color: $orderPhoneColor
            Payment Method: $orderPaymentMethod
            Delivery Type: $orderDeliveryType
            
            Order Timestamp: ${java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(java.util.Date(orderTimestamp))}
        """.trimIndent()

        orderHistoryTextView.text = orderHistoryText

        // Order Status TextView
        val orderStatusTextView: TextView = findViewById(R.id.orderStatusTextView)
        val orderStatus = when {
            canCancelOrder -> "Status: Ordered"
            currentTime - orderTimestamp > 24 * 60 * 60 * 1000 -> "Status: Delivered"
            else -> "Status: Cancelled"
        }
        orderStatusTextView.text = orderStatus

        // Cancel Order Button
        val cancelOrderButton: Button = findViewById(R.id.cancelOrderButton)
        cancelOrderButton.visibility = if (canCancelOrder) View.VISIBLE else View.GONE

        cancelOrderButton.setOnClickListener {
            // Logic to cancel the order
            val sharedPreferencesEditor = sharedPreferences.edit()
            sharedPreferencesEditor.remove("ORDER_PHONE_BRAND")
            sharedPreferencesEditor.remove("ORDER_PHONE_MODEL")
            sharedPreferencesEditor.remove("ORDER_PHONE_PRICE")
            sharedPreferencesEditor.remove("ORDER_PHONE_STORAGE")
            sharedPreferencesEditor.remove("ORDER_PHONE_COLOR")
            sharedPreferencesEditor.remove("ORDER_PAYMENT_METHOD")
            sharedPreferencesEditor.remove("ORDER_DELIVERY_TYPE")
           // sharedPreferences.remove("ORDER_TIMESTAMP")
            sharedPreferencesEditor.apply()

            // Update the status to "Cancelled"
            orderStatusTextView.text = "Status: Cancelled"

            // Show confirmation or notify the user
            Toast.makeText(this, "Order cancelled successfully", Toast.LENGTH_SHORT).show()
        }

    }
}
