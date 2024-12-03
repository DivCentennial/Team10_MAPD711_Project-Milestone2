package com.divyanshoo.team10_mapd711_project_milestone2.repository

import android.content.Context
import com.divyanshoo.team10_mapd711_project_milestone2.R
import com.divyanshoo.team10_mapd711_project_milestone2.model.PhoneModel

class PhoneRepository(private val context: Context) {

    // Functions for retrieving brands, models, or other phone-related data
    fun getPhoneBrands(): List<String> {
        return context.resources.getStringArray(R.array.brands).toList()
    }

    fun getPhoneModelsByBrand(brand: String): List<String> {
        return when (brand) {
            "Samsung" -> listOf("Galaxy S21", "Galaxy Z Flip", "Galaxy Note 20")
            "Google Pixel" -> listOf("Pixel 6", "Pixel 6 Pro", "Pixel 5a")
            "iPhone" -> listOf("iPhone 13", "iPhone 12", "iPhone SE")
            else -> emptyList()
        }
    }
    fun getPhoneModelsForBrand(brand: String): List<PhoneModel> {
        return when (brand) {
            "Samsung" -> listOf(
                PhoneModel("Galaxy S23", "799"),
                PhoneModel("Galaxy Z Fold 5", "1799"),
                PhoneModel("Galaxy Z Fold 6", "1899"),
                PhoneModel("Galaxy S23 Ultra", "1099")
            )
            "Google Pixel" -> listOf(
                PhoneModel("Google Pixel 8", "699"),
                PhoneModel("Google Pixel 7 Pro", "899"),
                PhoneModel("Google Pixel 6", "599"),
                PhoneModel("Google Pixel 9", "1099")
            )
            "iPhone" -> listOf(
                PhoneModel("iPhone 14", "999"),
                PhoneModel("iPhone 15", "1099"),
                PhoneModel("iPhone 15 Pro", "1199"),
                PhoneModel("iPhone 16", "1299")
            )
            else -> emptyList()
        }
    }
    fun getPhoneDetails(model: String): String {
        return when (model) {
            "Galaxy S21" -> "Flagship phone with Snapdragon 888"
            "Pixel 6" -> "Google's latest with Tensor chip"
            "iPhone 13" -> "Apple's premium device"
            else -> "Details not available"
        }
    }
}
