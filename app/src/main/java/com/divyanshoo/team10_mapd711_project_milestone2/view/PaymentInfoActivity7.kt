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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.divyanshoo.team10_mapd711_project_milestone2.R
import com.divyanshoo.team10_mapd711_project_milestone2.viewmodel.PaymentInfoViewModel
import com.divyanshoo.team10_mapd711_project_milestone2.viewmodel.PaymentInfoViewModelFactory

class PaymentInfoActivity7 : AppCompatActivity() {

    private val paymentInfoViewModel: PaymentInfoViewModel by viewModels {
        PaymentInfoViewModelFactory()
    }

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

        // Observe the LiveData from ViewModel
        paymentInfoViewModel.cardDetailsVisible.observe(this, Observer { isVisible ->
            if (isVisible) {
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
        })

        paymentInfoViewModel.validationError.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        // Show or hide fields based on the selected payment method
        paymentMethodRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedPaymentMethod: RadioButton = findViewById(checkedId)
            val paymentMethodText = selectedPaymentMethod.text.toString()
            paymentInfoViewModel.updatePaymentMethod(paymentMethodText)
        }

        submitButton.setOnClickListener {
            val selectedPaymentMethodId = paymentMethodRadioGroup.checkedRadioButtonId
            val selectedPaymentMethod: RadioButton = findViewById(selectedPaymentMethodId)
            val paymentMethod = selectedPaymentMethod.text.toString()

            // Get card details from EditText fields
            val cardNumber = cardNumberEditText.text.toString()
            val expiryDate = expiryDateEditText.text.toString()
            val cardHolderName = cardHolderNameEditText.text.toString()
            val cvv = cvvEditText.text.toString()

            // Validate payment details using ViewModel
            if (paymentInfoViewModel.validatePaymentInfo(paymentMethod, cardNumber, expiryDate, cardHolderName, cvv)) {
                // Proceed with the checkout process
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
                val deliveryType = intent.getStringExtra("DELIVERY_TYPE")
                val campusName = intent.getStringExtra("CAMPUS_NAME")

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
                intent.putExtra("DELIVERY_TYPE", deliveryType)
                intent.putExtra("CAMPUS_NAME", campusName)

                // Start the OrderConfirmation8 activity
                startActivity(intent)

                // Show a success message
                Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
