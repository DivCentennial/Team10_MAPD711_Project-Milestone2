package com.divyanshoo.team10_mapd711_project_milestone2.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.divyanshoo.team10_mapd711_project_milestone2.model.AppDatabase
import com.divyanshoo.team10_mapd711_project_milestone2.model.Phone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class BrandSelectionViewModel(application: Application) : AndroidViewModel(application) {

    // Function to save selected brand to the database
    fun saveSelectedBrandToDatabase(brand: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val phoneDao = AppDatabase.getDatabase(getApplication()).phoneDao()

                // Check if a phone with the same brand already exists
                val existingPhone = phoneDao.getPhonesByBrand(brand).firstOrNull()
                if (existingPhone == null) {
                    val phone = Phone(
                        productId = UUID.randomUUID(),
                        phoneMake = brand,
                        phoneModel = "",
                        phoneColor = "",
                        storageCapacity = "",
                        price = ""
                    )

                    // Log the selected brand
                    Log.d("BrandSelectionViewModel", "Saving brand: $brand")

                    // Save the phone details to the database
                    phoneDao.insertPhone(phone)
                } else {
                    // Log if the brand already exists
                    Log.d("BrandSelectionViewModel", "Brand already exists in the database: $brand")
                }
            }
        }
    }
}
