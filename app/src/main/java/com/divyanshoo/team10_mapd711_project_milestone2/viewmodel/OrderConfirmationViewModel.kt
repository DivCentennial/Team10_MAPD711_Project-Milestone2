package com.divyanshoo.team10_mapd711_project_milestone2.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.divyanshoo.team10_mapd711_project_milestone2.model.User
import com.divyanshoo.team10_mapd711_project_milestone2.model.Phone
import com.divyanshoo.team10_mapd711_project_milestone2.repository.UserRepository
import com.divyanshoo.team10_mapd711_project_milestone2.repository.PhoneRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class OrderConfirmationViewModel(
    private val userRepository: UserRepository,
    private val phoneRepository: PhoneRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _orderSummary = MutableLiveData<String>()
    val orderSummary: LiveData<String> get() = _orderSummary

    private val _customerInfo = MutableLiveData<String>()
    val customerInfo: LiveData<String> get() = _customerInfo

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchOrderDetails(phoneProductId: UUID, paymentMethod: String, deliveryType: String, campusName: String?, phoneBrand: String, phoneModel: String, phonePrice: String, phoneStorage: String, phoneColor: String) {
        viewModelScope.launch {
            try {
                Log.d("OrderConfirmationVM", "Fetching order details for product ID: $phoneProductId")
                val loggedInUser = getLoggedInUser()
                val phone = getPhoneById(phoneProductId)

                phone?.let {
                    _orderSummary.value = """
                        Brand: $phoneBrand
                        Model: $phoneModel
                        Price: $phonePrice CAD
                        Storage: $phoneStorage
                        Color: $phoneColor
                    """.trimIndent()
                }

                loggedInUser?.let { user ->
                    val customerInfoText = if (deliveryType == "Door Delivery") {
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
                            Price(C$): $phonePrice CAD
                            Storage: $phoneStorage
                            Color: $phoneColor
                        """.trimIndent()
                    }
                    _customerInfo.value = customerInfoText
                } ?: run {
                    _error.value = "Failed to fetch user details."
                }

            } catch (e: Exception) {
                _error.value = "Error fetching order details: ${e.message}"
                Log.e("OrderConfirmationVM", "Error fetching order details: ${e.message}")
            }
        }
    }

    private suspend fun getLoggedInUser(): User? {
        return withContext(Dispatchers.IO) {
            val loggedInUserId = getLoggedInUserId()
            //userRepository.getUserById(loggedInUserId)
            val user = userRepository.getUserById(loggedInUserId)
            Log.d("OrderConfirmationVM", "Fetched user: $user")
            return@withContext user
        }
    }

    private suspend fun getPhoneById(productId: UUID): Phone? {
        return withContext(Dispatchers.IO) {
            phoneRepository.getPhoneById(productId)
        }
    }

    private fun getLoggedInUserId(): Int {
        return sharedPreferences.getInt("LOGGED_IN_USER_ID", -1)
    }

    fun saveOrderToSharedPreferences(
        phoneBrand: String, phoneModel: String, phonePrice: String, phoneStorage: String, phoneColor: String, paymentMethod: String, deliveryType: String
    ) {
        val sharedPreferencesEditor = sharedPreferences.edit()

        sharedPreferencesEditor.putString("ORDER_PHONE_BRAND", phoneBrand)
        sharedPreferencesEditor.putString("ORDER_PHONE_MODEL", phoneModel)
        sharedPreferencesEditor.putString("ORDER_PHONE_PRICE", phonePrice)
        sharedPreferencesEditor.putString("ORDER_PHONE_STORAGE", phoneStorage)
        sharedPreferencesEditor.putString("ORDER_PHONE_COLOR", phoneColor)
        sharedPreferencesEditor.putString("ORDER_PAYMENT_METHOD", paymentMethod)
        sharedPreferencesEditor.putString("ORDER_DELIVERY_TYPE", deliveryType)

        val orderTimestamp = System.currentTimeMillis()
        sharedPreferencesEditor.putLong("ORDER_TIMESTAMP", orderTimestamp)

        viewModelScope.launch {
            val loggedInUser = getLoggedInUser()
            loggedInUser?.let { user ->
                sharedPreferencesEditor.putString("ORDER_USER_NAME", "${user.firstname} ${user.lastname}")
                sharedPreferencesEditor.putString("ORDER_USER_ADDRESS", "${user.address}, ${user.city}, ${user.postalCode}")
            }
        }
        sharedPreferencesEditor.apply()
    }
}
