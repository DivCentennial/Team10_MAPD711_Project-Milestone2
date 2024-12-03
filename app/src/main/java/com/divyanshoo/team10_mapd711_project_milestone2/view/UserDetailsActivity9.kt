package com.divyanshoo.team10_mapd711_project_milestone2.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.divyanshoo.team10_mapd711_project_milestone2.R
import com.divyanshoo.team10_mapd711_project_milestone2.model.AppDatabase
import com.divyanshoo.team10_mapd711_project_milestone2.model.User
import com.divyanshoo.team10_mapd711_project_milestone2.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDetailsActivity9 : AppCompatActivity() {

    private lateinit var userRepository: UserRepository
    private lateinit var userDetailsTextView: TextView
    private lateinit var firstnameEditText: EditText
    private lateinit var lastnameEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var cityEditText: EditText
    private lateinit var postalCodeEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var saveButton: Button
    private lateinit var proceedButton: Button
    private var currentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details9)

        userRepository = UserRepository(AppDatabase.getDatabase(this).userDao())

       // userDetailsTextView = findViewById(R.id.tv_user_details)
        firstnameEditText = findViewById(R.id.et_first_name_value)
        lastnameEditText = findViewById(R.id.et_last_name_value) // Assuming you have a field for last name
        addressEditText = findViewById(R.id.et_address_value)
        cityEditText = findViewById(R.id.et_city_value)
        postalCodeEditText = findViewById(R.id.et_postal_code_value) // Assuming you have a postal code field
        updateButton = findViewById(R.id.btn_edit_all)
        saveButton = findViewById(R.id.btn_save)
        proceedButton = findViewById(R.id.btn_proceed_brand_selection)

        val email = intent.getStringExtra("USER_EMAIL") ?: return

        // Fetch user details from the database and display them
        CoroutineScope(Dispatchers.IO).launch {
            val user = userRepository.getUserByEmail(email)
            runOnUiThread {
                user?.let {
                    currentUser = it
//                    userDetailsTextView.text = """
//                        Customer ID: ${it.custId}
//                        Name: ${it.firstname} ${it.lastname}
//                        Address: ${it.address}, ${it.city}, ${it.postalCode}
//                        Email: ${it.email}
//                    """.trimIndent()

                    // Pre-fill the input fields with the user's existing details
                    firstnameEditText.setText(it.firstname)
                    lastnameEditText.setText(it.lastname)
                    addressEditText.setText(it.address)
                    cityEditText.setText(it.city)
                    postalCodeEditText.setText(it.postalCode)
                }
            }
        }

        // Enable editing when the "Edit" button is clicked
        updateButton.setOnClickListener {
            firstnameEditText.isEnabled = true
            lastnameEditText.isEnabled = true
            addressEditText.isEnabled = true
            cityEditText.isEnabled = true
            postalCodeEditText.isEnabled = true
            saveButton.visibility = Button.VISIBLE
        }

        // Save the updated user details when the "Save" button is clicked
        saveButton.setOnClickListener {
            val updatedFirstname = firstnameEditText.text.toString()
            val updatedLastname = lastnameEditText.text.toString()
            val updatedAddress = addressEditText.text.toString()
            val updatedCity = cityEditText.text.toString()
            val updatedPostalCode = postalCodeEditText.text.toString()

            // Validate the input fields
            if (updatedFirstname.isEmpty() || updatedLastname.isEmpty() || updatedAddress.isEmpty() || updatedCity.isEmpty() || updatedPostalCode.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Update the user details in the database
            currentUser?.let { user ->
                val updatedUser = user.copy(
                    firstname = updatedFirstname,
                    lastname = updatedLastname,
                    address = updatedAddress,
                    city = updatedCity,
                    postalCode = updatedPostalCode
                )

                CoroutineScope(Dispatchers.IO).launch {
                    userRepository.saveUser(updatedUser) // This will now perform an update
                    runOnUiThread {
                        Toast.makeText(this@UserDetailsActivity9, "User details updated", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            // Optionally, hide save button and enable proceed button after saving
            saveButton.visibility = Button.GONE
            proceedButton.visibility = Button.VISIBLE
        }


        // Proceed to the next activity after saving details
        proceedButton.setOnClickListener {
            // Example: Navigate to the brand selection page
            val intent = Intent(this, BrandSelectionActivity2::class.java)
            startActivity(intent)
        }
    }
}
