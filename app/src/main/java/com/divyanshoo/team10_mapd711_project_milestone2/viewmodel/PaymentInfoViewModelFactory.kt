package com.divyanshoo.team10_mapd711_project_milestone2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PaymentInfoViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PaymentInfoViewModel::class.java)) {
            return PaymentInfoViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
