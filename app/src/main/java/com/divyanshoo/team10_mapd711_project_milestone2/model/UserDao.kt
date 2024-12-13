package com.divyanshoo.team10_mapd711_project_milestone2.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)  // This will insert if the user doesn't exist, or update if the user exists
    suspend fun insertOrUpdateUser(user: User) // Insert or update user depending on existence
    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun getUser(email: String, password: String): User? // Get user by credentials

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User? // Fetch user by email (for details display)

    // Method to get a user by ID
    @Query("SELECT * FROM users WHERE custId = :userId LIMIT 1")
    suspend fun getUserById(userId: Int): User?
}

