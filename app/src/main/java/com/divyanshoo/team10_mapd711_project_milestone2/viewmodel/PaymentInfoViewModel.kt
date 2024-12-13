package com.divyanshoo.team10_mapd711_project_milestone2.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.divyanshoo.team10_mapd711_project_milestone2.view.PaymentInfoActivity7

class PaymentInfoViewModel : ViewModel() {
    val paymentMethod = MutableLiveData<String>()
    val cardDetailsVisible = MutableLiveData<Boolean>()
    val validationError = MutableLiveData<String?>()

    fun validatePaymentInfo(
        paymentMethod: String,
        cardNumber: String?,
        expiryDate: String?,
        cardHolderName: String?,
        cvv: String?
    ): Boolean {
        if (paymentMethod == "Credit Card 💳" || paymentMethod == "Debit Card 💳") {
            if (cardNumber.isNullOrEmpty() || expiryDate.isNullOrEmpty() || cardHolderName.isNullOrEmpty() || cvv.isNullOrEmpty()) {
                validationError.value = "Please fill in all card details."
                return false
            }
        }
        validationError.value = null // No error
        return true
    }

    fun updatePaymentMethod(selectedPaymentMethod: String) {
        paymentMethod.value = selectedPaymentMethod
        cardDetailsVisible.value = selectedPaymentMethod == "Credit Card 💳" || selectedPaymentMethod == "Debit Card 💳"
    }
}
