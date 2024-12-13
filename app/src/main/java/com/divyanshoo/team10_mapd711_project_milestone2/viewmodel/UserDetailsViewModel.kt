package com.divyanshoo.team10_mapd711_project_milestone2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.divyanshoo.team10_mapd711_project_milestone2.model.AppDatabase
import com.divyanshoo.team10_mapd711_project_milestone2.model.User
import com.divyanshoo.team10_mapd711_project_milestone2.repository.UserRepository
import kotlinx.coroutines.launch

class UserDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository: UserRepository = UserRepository(AppDatabase.getDatabase(application).userDao())

    private val _userDetails = MutableLiveData<User?>()
    val userDetails: LiveData<User?> = _userDetails

    private val _isUserUpdated = MutableLiveData<Boolean>()
    val isUserUpdated: LiveData<Boolean> = _isUserUpdated

    fun fetchUserByEmail(email: String) {
        viewModelScope.launch {
            val user = userRepository.getUserByEmail(email)
            _userDetails.postValue(user)
        }
    }

    fun updateUserDetails(user: User) {
        viewModelScope.launch {
            userRepository.saveUser(user)
            _isUserUpdated.postValue(true)
        }
    }
}
