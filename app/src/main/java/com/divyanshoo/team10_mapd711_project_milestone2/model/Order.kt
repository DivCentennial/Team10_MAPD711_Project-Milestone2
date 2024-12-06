package com.divyanshoo.team10_mapd711_project_milestone2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val orderId: Int = 0,
    val custId: Int,
    val productId: UUID,
    val orderDate: Long, // Unix timestamp for date
    val totalAmount: Double,
    val status: String // e.g., "Ordered", "Cancelled", "Delivered"
)
