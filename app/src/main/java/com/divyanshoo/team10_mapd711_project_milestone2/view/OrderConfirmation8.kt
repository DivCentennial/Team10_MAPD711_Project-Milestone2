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
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)

        // Properly initialize userRepository
        val userDao = AppDatabase.getDatabase(this).userDao()
        userRepository = UserRepository(userDao) // Ensure this is not commented out

        phoneRepository = PhoneRepository(application)

        // Retrieving data passed via Intent
        val phoneProductId = intent.getStringExtra("SELECTED_PRODUCT_ID")?.let { UUID.fromString(it) }
        Log.d("OrderConfirmation8", "Phone Product ID: $phoneProductId")
        val paymentMethod = intent.getStringExtra("paymentMethod")
        val deliveryType = intent.getStringExtra("DELIVERY_TYPE")
        val campusName = intent.getStringExtra("CAMPUS_NAME")
        val phoneBrand = intent.getStringExtra("SELECTED_BRAND")
        val phoneModel = intent.getStringExtra("phoneModel")
        val phonePrice = intent.getStringExtra("phonePrice")
        val phoneStorage = intent.getStringExtra("phoneStorage")
        val phoneColor = intent.getStringExtra("phoneColor")

        Log.d("OrderConfirmation8", "Received Brand: $phoneBrand")
        Log.d("OrderConfirmation8", "Received Model: $phoneModel")
        Log.d("OrderConfirmation8", "Received Price: $phonePrice")
        Log.d("OrderConfirmation8", "Received Storage: $phoneStorage")
        Log.d("OrderConfirmation8", "Received Color: $phoneColor")


        // View references
        val orderSummaryTextView: TextView = findViewById(R.id.orderSummaryTextView)
        val customerInfoTextView: TextView = findViewById(R.id.customerInfoTextView)
        val homeButton: Button = findViewById(R.id.homeButton)

        // Fetch logged-in user data and phone details from the database
        lifecycleScope.launch {
            val loggedInUser = getLoggedInUser()
            val phone = phoneProductId?.let { getPhoneById(it) }

            // Log the phone details for debugging
            Log.d("OrderConfirmation8", "Retrieved phone details: $phone")

            // Populate order summary
            phone?.let {
                orderSummaryTextView.text = """
                Brand: $phoneBrand
    Model: $phoneModel
    Price: $phonePrice USD
    Storage: $phoneStorage
    Color: $phoneColor
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
                
                Phone Details 📲:
                Brand: $phoneBrand
                Model: $phoneModel
                Price(C$): $phonePrice CAD
                Storage: $phoneStorage
                Color: $phoneColor
                
                  """.trimIndent()
                } else {
                    """
                Customer Name: ${user.firstname} ${user.lastname}
                Customer Address: ${user.address}, ${user.city}, ${user.postalCode}
                Email: ${user.email}
                Payment Method: $paymentMethod
                Delivery Type: $deliveryType
                Campus Name: $campusName
                
                Phone Details 📲:
                Brand: $phoneBrand
                Model: $phoneModel
                Price(C${'$'}): $phonePrice CAD
                Storage: $phoneStorage
                Color: $phoneColor
                
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
        val loggedInUserId = sharedPreferences.getInt("LOGGED_IN_USER_ID", -1)
        if (loggedInUserId == -1) {
            // Log an error for debugging if no user ID is found
            Log.e("OrderConfirmation8", "No user ID found in SharedPreferences.")
        } else {
            // Log the retrieved user ID for verification
            Log.d("OrderConfirmation8", "Logged-in user ID: $loggedInUserId")
        }
        return loggedInUserId
    }

}
