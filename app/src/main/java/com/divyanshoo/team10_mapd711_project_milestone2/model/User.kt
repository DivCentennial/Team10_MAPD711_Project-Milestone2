package com.divyanshoo.team10_mapd711_project_milestone2.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val custId: Int = 0,
    val email: String, // Using email as the username
    val password: String,
    val firstname: String,
    val lastname: String,
    val address: String,
    val city: String,
    val postalCode: String
)
