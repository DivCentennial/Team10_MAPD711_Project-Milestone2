package com.divyanshoo.team10_mapd711_project_milestone2.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat

class OrderHistoryViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {

    private val _orderDetails = MutableLiveData<String>()
    val orderDetails: LiveData<String> get() = _orderDetails

    private val _orderStatus = MutableLiveData<String>()
    val orderStatus: LiveData<String> get() = _orderStatus

    private val _canCancelOrder = MutableLiveData<Boolean>()
    val canCancelOrder: LiveData<Boolean> get() = _canCancelOrder

    fun loadOrderDetails() {
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
        val canCancel = currentTime - orderTimestamp <= 24 * 60 * 60 * 1000 // 24 hours in milliseconds
        _canCancelOrder.value = canCancel

        // Create order details string
        val orderDetailsText = """
            Order Summary:
            Phone Brand: $orderPhoneBrand
            Phone Model: $orderPhoneModel
            Price: $orderPhonePrice
            Storage: $orderPhoneStorage
            Color: $orderPhoneColor
            Payment Method: $orderPaymentMethod
            Delivery Type: $orderDeliveryType

            Order Timestamp: ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(java.util.Date(orderTimestamp))}
        """.trimIndent()

        _orderDetails.value = orderDetailsText

        // Determine order status based on the time elapsed since the order
        val status = when {
            canCancel -> "Status: Ordered"
            currentTime - orderTimestamp > 24 * 60 * 60 * 1000 -> "Status: Delivered"
            else -> "Status: Cancelled"
        }
        _orderStatus.value = status
    }

    fun cancelOrder() {
        val sharedPreferencesEditor = sharedPreferences.edit()
        sharedPreferencesEditor.remove("ORDER_PHONE_BRAND")
        sharedPreferencesEditor.remove("ORDER_PHONE_MODEL")
        sharedPreferencesEditor.remove("ORDER_PHONE_PRICE")
        sharedPreferencesEditor.remove("ORDER_PHONE_STORAGE")
        sharedPreferencesEditor.remove("ORDER_PHONE_COLOR")
        sharedPreferencesEditor.remove("ORDER_PAYMENT_METHOD")
        sharedPreferencesEditor.remove("ORDER_DELIVERY_TYPE")
        sharedPreferencesEditor.remove("ORDER_TIMESTAMP") // Optionally remove timestamp as well
        sharedPreferencesEditor.apply()

        // Update status to "Cancelled"
        _orderStatus.value = "Status: Cancelled"
    }
}
