package com.divyanshoo.team10_mapd711_project_milestone2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CheckoutViewModel(application: Application) : AndroidViewModel(application) {

    private val _selectedPhoneDetails = MutableLiveData<Map<String, String>>()
    val selectedPhoneDetails: LiveData<Map<String, String>> get() = _selectedPhoneDetails

    fun setSelectedPhoneDetails(
        selectedBrand: String?,
        selectedModel: String?,
        selectedStorage: String?,
        selectedColor: String?,
        selectedPrice: String?
    ) {
        val details = mapOf(
            "brand" to (selectedBrand ?: "N/A"),
            "model" to (selectedModel ?: "N/A"),
            "storage" to (selectedStorage ?: "N/A"),
            "color" to (selectedColor ?: "N/A"),
            "price" to (selectedPrice ?: "N/A")
        )

        _selectedPhoneDetails.value = details
    }
}
