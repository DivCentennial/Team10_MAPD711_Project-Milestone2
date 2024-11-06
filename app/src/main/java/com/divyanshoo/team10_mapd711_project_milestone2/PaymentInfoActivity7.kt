//PaymentInfoActivity7.kt
package com.divyanshoo.team10_mapd711_project_milestone2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PaymentInfoActivity7 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_info7)

        val paymentMethodRadioGroup: RadioGroup = findViewById(R.id.paymentMethodRadioGroup)
        val cardNumberEditText: EditText = findViewById(R.id.cardNumberEditText)
        val expiryDateEditText: EditText = findViewById(R.id.expiryDateEditText)
        val submitButton: Button = findViewById(R.id.submitButton2)

        // Initially, hide the card details input fields
        cardNumberEditText.visibility = View.GONE
        expiryDateEditText.visibility = View.GONE

        // Set up a listener to handle payment method selection
        paymentMethodRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedPaymentMethod: RadioButton = findViewById(checkedId)
            val paymentMethodText = selectedPaymentMethod.text.toString()

            // Show card details input fields if Credit or Debit is selected
            if (paymentMethodText == "Credit Card 💳" || paymentMethodText == "Debit Card 💳") {
                cardNumberEditText.visibility = View.VISIBLE
                expiryDateEditText.visibility = View.VISIBLE
            } else {
                // Hide card details input fields if Apple Pay or Google Pay is selected
                cardNumberEditText.visibility = View.GONE
                expiryDateEditText.visibility = View.GONE
            }
        }

        // Set up submit button to handle the checkout process
        submitButton.setOnClickListener {
            // Get the selected payment method
            val selectedPaymentMethodId = paymentMethodRadioGroup.checkedRadioButtonId
            val selectedPaymentMethod: RadioButton = findViewById(selectedPaymentMethodId)
            val paymentMethod = selectedPaymentMethod.text.toString()

            if (paymentMethod == "Credit Card 💳" || paymentMethod == "Debit Card 💳") {
                // Get card details
                val cardNumber = cardNumberEditText.text.toString()
                val expiryDate = expiryDateEditText.text.toString()

                if (cardNumber.isEmpty() || expiryDate.isEmpty()) {
                    Toast.makeText(this, "Please fill in card details.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            // Assuming you've already retrieved the previous details such as phone details, customer info, etc.
            // Retrieving the passed data
            val customerName = intent.getStringExtra("fullName")
            val streetName = intent.getStringExtra("streetName")
            val city = intent.getStringExtra("city")
            val postalCode = intent.getStringExtra("postalCode")
            val deliveryAddress = "$streetName, $city, $postalCode"
            val phoneBrand = intent.getStringExtra("SELECTED_BRAND")
            val phoneModel = intent.getStringExtra("SELECTED_MODEL")
            val phoneStorage = intent.getStringExtra("SELECTED_STORAGE")
            val phoneColor = intent.getStringExtra("SELECTED_COLOR")
            val phonePrice = intent.getStringExtra("selectedPrice")

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

            // Start the OrderConfirmation8 activity
            startActivity(intent)

            // Show a success message
            Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show()
        }

    }
}