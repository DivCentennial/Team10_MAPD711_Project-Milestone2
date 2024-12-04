package com.divyanshoo.team10_mapd711_project_milestone2.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.UUID

@Dao
interface PhoneDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhone(phone: Phone) : Long

    @Query("SELECT * FROM phones WHERE productId = :productId")
    suspend fun getPhoneById(productId: UUID): Phone?

    @Query("SELECT * FROM phones WHERE phoneMake = :brand")
    suspend fun getPhonesByBrand(brand: String): List<Phone>

    @Query("SELECT * FROM phones")
    suspend fun getAllPhones(): List<Phone>

    @Query("SELECT DISTINCT phoneMake FROM phones")
    fun getDistinctBrands(): List<String>
}
