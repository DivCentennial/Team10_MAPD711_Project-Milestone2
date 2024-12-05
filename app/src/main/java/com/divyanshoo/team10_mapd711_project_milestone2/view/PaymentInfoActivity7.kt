//PaymentInfoActivity7.kt

// View description: User can select payment method and enter card details.
package com.divyanshoo.team10_mapd711_project_milestone2.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.divyanshoo.team10_mapd711_project_milestone2.R

class PaymentInfoActivity7 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

                super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_info7)

        val paymentMethodRadioGroup: RadioGroup = findViewById(R.id.paymentMethodRadioGroup)
        val cardNumberEditText: EditText = findViewById(R.id.cardNumberEditText)
        val expiryDateEditText: EditText = findViewById(R.id.expiryDateEditText)
        val cardHolderNameEditText: EditText = findViewById(R.id.cardHolderNameEditText)
        val cvvEditText: EditText = findViewById(R.id.cvvEditText)
        val submitButton: Button = findViewById(R.id.submitButton2)

        // Initially hide all card details input fields
        cardNumberEditText.visibility = View.GONE
        expiryDateEditText.visibility = View.GONE
        cardHolderNameEditText.visibility = View.GONE
        cvvEditText.visibility = View.GONE

        // Show or hide fields based on the selected payment method
        paymentMethodRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedPaymentMethod: RadioButton = findViewById(checkedId)
            val paymentMethodText = selectedPaymentMethod.text.toString()

            if (paymentMethodText == "Credit Card 💳" || paymentMethodText == "Debit Card 💳") {
                cardNumberEditText.visibility = View.VISIBLE
                expiryDateEditText.visibility = View.VISIBLE
                cardHolderNameEditText.visibility = View.VISIBLE
                cvvEditText.visibility = View.VISIBLE
            } else {
                cardNumberEditText.visibility = View.GONE
                expiryDateEditText.visibility = View.GONE
                cardHolderNameEditText.visibility = View.GONE
                cvvEditText.visibility = View.GONE
            }
        }


        // Set up submit button to handle the checkout process
        submitButton.setOnClickListener {
            // Get the selected payment method
            val selectedPaymentMethodId = paymentMethodRadioGroup.checkedRadioButtonId
            val selectedPaymentMethod: RadioButton = findViewById(selectedPaymentMethodId)
            val paymentMethod = selectedPaymentMethod.text.toString()
            val cardHolderNameEditText: EditText = findViewById(R.id.cardHolderNameEditText)
            val cvvEditText: EditText = findViewById(R.id.cvvEditText)


            // Validate payment method
            if (paymentMethod == "Credit Card 💳" || paymentMethod == "Debit Card 💳") {
                // Get card details
                val cardNumber = cardNumberEditText.text.toString()
                val expiryDate = expiryDateEditText.text.toString()
                val cardHolderName = cardHolderNameEditText.text.toString()
                val cvv = cvvEditText.text.toString()

                // Validate card details
                if (cardNumber.isEmpty() || expiryDate.isEmpty() || cardHolderName.isEmpty() || cvv.isEmpty()) {
                    Toast.makeText(this, "Please fill in all card details.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            // Retrieve the passed data
            val customerName = intent.getStringExtra("fullName")
            val streetName = intent.getStringExtra("streetName")
            val city = intent.getStringExtra("city")
            val postalCode = intent.getStringExtra("postalCode")
            val deliveryAddress = "$streetName, $city, $postalCode"
            val phoneBrand = intent.getStringExtra("SELECTED_BRAND")
            val phoneModel = intent.getStringExtra("SELECTED_MODEL")
            val phoneStorage = intent.getStringExtra("SELECTED_STORAGE")
            val phoneColor = intent.getStringExtra("SELECTED_COLOR")
            val phonePrice = intent.getStringExtra("SELECTED_PRICE")
            val email = intent.getStringExtra("email")
            val phone = intent.getStringExtra("phone")
            val deliveryType = intent.getStringExtra("DELIVERY_TYPE") // Ensure these are passed
            val campusName = intent.getStringExtra("CAMPUS_NAME")     // Ensure these are passed

            // Log the values to verify if they are passed correctly
            Log.d("PaymentInfoActivity7", "Brand: $phoneBrand")
            Log.d("PaymentInfoActivity7", "Model: $phoneModel")
            Log.d("PaymentInfoActivity7", "Storage: $phoneStorage")
            Log.d("PaymentInfoActivity7", "Color: $phoneColor")
            Log.d("PaymentInfoActivity7", "Price: $phonePrice")

            // Create an Intent to navigate to OrderConfirmation8
            val intent = Intent(this, OrderConfirmation8::class.java)

            // Passing data via Intent
            intent.putExtra("SELECTED_BRAND", phoneBrand)
            intent.putExtra("phoneModel", phoneModel)
            intent.putExtra("phonePrice", phonePrice)
            intent.putExtra("phoneStorage", phoneStorage)
            intent.putExtra("phoneColor", phoneColor)
            intent.putExtra("customerName", customerName)
            intent.putExtra("deliveryAddress", deliveryAddress)
            intent.putExtra("email", email)
            intent.putExtra("phone", phone)
            intent.putExtra("paymentMethod", paymentMethod)
            intent.putExtra("DELIVERY_TYPE", deliveryType) // Pass these values
            intent.putExtra("CAMPUS_NAME", campusName)     // Pass these values

            // Start the OrderConfirmation8 activity
            startActivity(intent)

            // Show a success message
            Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show()
        }


    }
}