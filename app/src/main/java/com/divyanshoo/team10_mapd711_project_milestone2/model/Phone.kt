package com.divyanshoo.team10_mapd711_project_milestone2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.UUID

@Entity(tableName = "phones")
data class Phone(
    @PrimaryKey val productId: UUID,
    val phoneMake: String,
    val phoneModel: String,
    val phoneColor: String,
    val storageCapacity: String,
    val price: String
)
