//OrderConfirmation8.kt

// View description: User can see the order confirmation phone details, customer information, and payment method.
//OrderConfirmation8.kt
package com.divyanshoo.team10_mapd711_project_milestone2.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.divyanshoo.team10_mapd711_project_milestone2.R
import com.divyanshoo.team10_mapd711_project_milestone2.model.AppDatabase
import com.divyanshoo.team10_mapd711_project_milestone2.model.User
import com.divyanshoo.team10_mapd711_project_milestone2.model.Phone
import com.divyanshoo.team10_mapd711_project_milestone2.repository.UserRepository
import com.divyanshoo.team10_mapd711_project_milestone2.repository.PhoneRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class OrderConfirmation8 : AppCompatActivity() {

    private lateinit var userRepository: UserRepository
    private lateinit var phoneRepository: PhoneRepository
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirmation8)

        // Initialize SharedPreferences and Repositories
        sharedPreferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE)

        // Properly initialize userRepository
        val userDao = AppDatabase.getDatabase(this).userDao()
        userRepository = UserRepository(userDao) // Ensure this is not commented out

        phoneRepository = PhoneRepository(application)

        // Retrieving data passed via Intent
        val phoneProductId = intent.getStringExtra("SELECTED_PRODUCT_ID")?.let { UUID.fromString(it) }
        val paymentMethod = intent.getStringExtra("paymentMethod")
        val deliveryType = intent.getStringExtra("DELIVERY_TYPE")
        val campusName = intent.getStringExtra("CAMPUS_NAME")

        // View references
        val orderSummaryTextView: TextView = findViewById(R.id.orderSummaryTextView)
        val customerInfoTextView: TextView = findViewById(R.id.customerInfoTextView)
        val homeButton: Button = findViewById(R.id.homeButton)

        // Fetch logged-in user data and phone details from the database
        lifecycleScope.launch {
            val loggedInUser = getLoggedInUser()
            val phone = phoneProductId?.let { getPhoneById(it) }

            // Populate order summary
            phone?.let {
                orderSummaryTextView.text = """
                Phone Brand: ${it.phoneMake}
                Model: ${it.phoneModel}
                Price: ${it.price}
                Storage: ${it.storageCapacity}
                Color: ${it.phoneColor}
            """.trimIndent()
            }

            // Populate customer info based on delivery type
            loggedInUser?.let { user ->
                val customerInfo = if (deliveryType == "Door Delivery") {
                    """
                Customer Name: ${user.firstname} ${user.lastname}
                Customer Address: ${user.address}, ${user.city}, ${user.postalCode}
                Email: ${user.email}
                Payment Method: $paymentMethod
                Delivery Type: $deliveryType
                """.trimIndent()
                } else {
                    """
                Customer Name: ${user.firstname} ${user.lastname}
                Customer Address: ${user.address}, ${user.city}, ${user.postalCode}
                Email: ${user.email}
                Payment Method: $paymentMethod
                Delivery Type: $deliveryType
                Campus Name: $campusName
                """.trimIndent()
                }
                customerInfoTextView.text = customerInfo
            } ?: run {
                customerInfoTextView.text = "Failed to fetch user details."
            }
        }

        // Add return to home page functionality
        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    private suspend fun getLoggedInUser(): User? {
        return withContext(Dispatchers.IO) {
            // Get the logged-in user's ID from SharedPreferences
            val loggedInUserId = getLoggedInUserId()
            // Fetch the user by ID from the repository
            userRepository.getUserById(loggedInUserId)
        }
    }

    private suspend fun getPhoneById(productId: UUID): Phone? {
        return withContext(Dispatchers.IO) {
            // Fetch phone by product ID from the repository
            phoneRepository.getPhoneById(productId)
        }
    }

    private fun getLoggedInUserId(): Int {
        // Fetch the logged-in user's ID from SharedPreferences
        return sharedPreferences.getInt("LOGGED_IN_USER_ID", -1)
    }
}
