//OrderConfirmation8.kt

// View description: User can see the order confirmation phone details, customer information, and payment method.
//OrderConfirmation8.kt
package com.divyanshoo.team10_mapd711_project_milestone2.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.divyanshoo.team10_mapd711_project_milestone2.R
import com.divyanshoo.team10_mapd711_project_milestone2.viewmodel.OrderConfirmationViewModel
import com.divyanshoo.team10_mapd711_project_milestone2.viewmodel.OrderConfirmationViewModelFactory
import com.divyanshoo.team10_mapd711_project_milestone2.repository.UserRepository
import com.divyanshoo.team10_mapd711_project_milestone2.repository.PhoneRepository
import com.divyanshoo.team10_mapd711_project_milestone2.model.AppDatabase
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.UUID

class OrderConfirmation8 : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    // Using the ViewModel by viewModels() delegate and passing the factory to it
    private val orderConfirmationViewModel: OrderConfirmationViewModel by viewModels {
        OrderConfirmationViewModelFactory(
            UserRepository(AppDatabase.getDatabase(this).userDao()),
            PhoneRepository(application),
            getSharedPreferences("user_prefs", MODE_PRIVATE)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirmation8)

        val orderSummaryTextView: TextView = findViewById(R.id.orderSummaryTextView)
        val customerInfoTextView: TextView = findViewById(R.id.customerInfoTextView)
        val homeButton: Button = findViewById(R.id.homeButton)
        val saveOrderButton: Button = findViewById(R.id.button2)
        val checkHistoryButton: Button = findViewById(R.id.button4)

        // Observe LiveData from ViewModel
        orderConfirmationViewModel.orderSummary.observe(this, Observer {
            orderSummaryTextView.text = it
        })

        orderConfirmationViewModel.customerInfo.observe(this, Observer {
            customerInfoTextView.text = it
        })

        orderConfirmationViewModel.error.observe(this, Observer { errorMessage ->
            Log.e("OrderConfirmation8", errorMessage)
        })

        // Retrieve data passed via Intent
        val phoneProductId = intent.getStringExtra("SELECTED_PRODUCT_ID")?.let { UUID.fromString(it) }
        val paymentMethod = intent.getStringExtra("paymentMethod") ?: ""
        val deliveryType = intent.getStringExtra("DELIVERY_TYPE") ?: ""
        val campusName = intent.getStringExtra("CAMPUS_NAME")
        val phoneBrand = intent.getStringExtra("SELECTED_BRAND") ?: ""
        val phoneModel = intent.getStringExtra("phoneModel") ?: ""
        val phonePrice = intent.getStringExtra("phonePrice") ?: ""
        val phoneStorage = intent.getStringExtra("phoneStorage") ?: ""
        val phoneColor = intent.getStringExtra("phoneColor") ?: ""

        // Fetch order details using the ViewModel
        phoneProductId?.let {
            orderConfirmationViewModel.fetchOrderDetails(
                it, paymentMethod, deliveryType, campusName, phoneBrand, phoneModel, phonePrice, phoneStorage, phoneColor
            )
        }

        // Save order to SharedPreferences using the ViewModel
        saveOrderButton.setOnClickListener {
            orderConfirmationViewModel.saveOrderToSharedPreferences(
                phoneBrand, phoneModel, phonePrice, phoneStorage, phoneColor, paymentMethod, deliveryType
            )
        }

        // Navigate to order history
        checkHistoryButton.setOnClickListener {
            val intent = Intent(this, OrderHistoryActivity::class.java)
            startActivity(intent)
        }

        // Navigate back to home
        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
