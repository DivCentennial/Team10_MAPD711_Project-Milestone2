package com.divyanshoo.team10_mapd711_project_milestone2.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface OrderDao {

    @Insert
    suspend fun insertOrder(order: Order)

    @Update
    suspend fun updateOrder(order: Order)

    @Query("SELECT * FROM orders WHERE custId = :custId ORDER BY orderDate DESC")
    suspend fun getOrdersByCustomer(custId: Int): List<Order>

    @Query("SELECT * FROM orders WHERE orderId = :orderId")
    suspend fun getOrderById(orderId: Int): Order?

    @Query("UPDATE orders SET status = 'Cancelled' WHERE orderId = :orderId")
    suspend fun cancelOrder(orderId: Int)

    @Query("UPDATE orders SET status = 'Delivered' WHERE orderId = :orderId")
    suspend fun markAsDelivered(orderId: Int)
}
