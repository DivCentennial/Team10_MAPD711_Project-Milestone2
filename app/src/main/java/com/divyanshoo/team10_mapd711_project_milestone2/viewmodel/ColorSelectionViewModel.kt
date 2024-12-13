package com.divyanshoo.team10_mapd711_project_milestone2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.divyanshoo.team10_mapd711_project_milestone2.model.Phone
import com.divyanshoo.team10_mapd711_project_milestone2.repository.PhoneRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class ColorSelectionViewModel(application: Application) : AndroidViewModel(application) {

    private val phoneRepository = PhoneRepository(application)
    private val _phoneSavedLiveData = MutableLiveData<Boolean>()
    val phoneSavedLiveData: LiveData<Boolean> get() = _phoneSavedLiveData

    fun savePhone(selectedBrand: String?, selectedModel: String?, selectedColor: String, selectedStorage: String?, selectedPrice: String?) {
        val phone = Phone(
            productId = UUID.randomUUID(),
            phoneMake = selectedBrand ?: "Unknown",
            phoneModel = selectedModel ?: "Unknown",
            phoneColor = selectedColor,
            storageCapacity = selectedStorage ?: "Unknown",
            price = selectedPrice?.toBigDecimalOrNull()?.toString() ?: "0"
        )

        CoroutineScope(Dispatchers.IO).launch {
            phoneRepository.insertPhone(phone)
            _phoneSavedLiveData.postValue(true) // Notify that the phone is saved
        }
    }
}
