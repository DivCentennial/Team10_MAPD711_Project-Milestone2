package com.divyanshoo.team10_mapd711_project_milestone2.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.divyanshoo.team10_mapd711_project_milestone2.repository.UserRepository
import com.divyanshoo.team10_mapd711_project_milestone2.repository.PhoneRepository

class OrderConfirmationViewModelFactory(
    private val userRepository: UserRepository,
    private val phoneRepository: PhoneRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderConfirmationViewModel::class.java)) {
            return OrderConfirmationViewModel(userRepository, phoneRepository, sharedPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
