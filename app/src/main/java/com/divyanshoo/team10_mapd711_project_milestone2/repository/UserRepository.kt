package com.divyanshoo.team10_mapd711_project_milestone2.repository

import com.divyanshoo.team10_mapd711_project_milestone2.model.User
import com.divyanshoo.team10_mapd711_project_milestone2.model.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {

    // Function to get a user by email and password
    suspend fun getUserByCredentials(email: String, password: String): User? {
        return userDao.getUser(email, password)
    }

    // Function to get a user by email only
    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    // Function to save a user
    suspend fun saveUser(user: User) {
        userDao.insertOrUpdateUser(user) // Use insert instead of update for new user registration
    }

    // Method to get a user by ID
    suspend fun getUserById(userId: Int): User? {
        return withContext(Dispatchers.IO) {
            userDao.getUserById(userId)
        }
    }

}
