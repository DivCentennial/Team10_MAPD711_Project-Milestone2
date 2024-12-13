package com.divyanshoo.team10_mapd711_project_milestone2.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.divyanshoo.team10_mapd711_project_milestone2.model.AppDatabase
import com.divyanshoo.team10_mapd711_project_milestone2.model.Phone
import com.divyanshoo.team10_mapd711_project_milestone2.model.PhoneModel
import com.divyanshoo.team10_mapd711_project_milestone2.repository.PhoneRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class ModelSelection3ViewModel(application: Application) : AndroidViewModel(application) {

    private val phoneRepository = PhoneRepository(application)
    private val _phoneModels = MutableLiveData<List<PhoneModel>>()
    val phoneModels: LiveData<List<PhoneModel>> = _phoneModels
    private val _finalPrice = MutableLiveData<String>()
    val finalPrice: LiveData<String> = _finalPrice
    private var selectedModel: PhoneModel? = null

    init {
        // Initialize phone models for a specific brand if needed
    }

    fun loadPhoneModelsForBrand(selectedBrand: String) {
        viewModelScope.launch {
            val models = phoneRepository.getPhoneModelsForBrand(selectedBrand)
            if (models.isEmpty()) {
                // Handle empty model list case, maybe notify UI
            } else {
                _phoneModels.value = models
            }
        }
    }

    fun selectPhoneModel(model: PhoneModel) {
        selectedModel = model
    }

    fun calculateFinalPrice(storage: String) {
        selectedModel?.let { model ->
            val basePrice = model.price.toIntOrNull() ?: 0
            val additionalCost = when (storage) {
                "128 GB" -> 30
                "256 GB" -> 50
                else -> 0
            }
            val finalPrice = basePrice + additionalCost
            _finalPrice.value = "Final Price: $$finalPrice"
        }
    }

    fun savePhoneDetailsToDatabase(
        selectedBrand: String,
        selectedStorage: String
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val phoneDao = AppDatabase.getDatabase(getApplication()).phoneDao()

                // Map PhoneModel to Phone
                val phone = Phone(
                    productId = UUID.randomUUID(),
                    phoneMake = selectedBrand,
                    phoneModel = selectedModel?.name ?: "", // Using 'name' from PhoneModel
                    phoneColor = "", // Color will be selected in the next screen
                    storageCapacity = selectedStorage,
                    price = _finalPrice.value?.removePrefix("Final Price: $") ?: ""
                )

                // Log the phone data to verify it's being passed correctly
                Log.d("PhoneInsertion", "Saving phone: $phone")
                phoneDao.insertPhone(phone)

                // Query all phones to verify the insert
                val allPhones = phoneDao.getAllPhones()
                Log.d("PhoneInsertion", "All phones in DB: $allPhones")
            }
        }
    }
}
