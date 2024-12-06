package com.divyanshoo.team10_mapd711_project_milestone2.repository

import com.divyanshoo.team10_mapd711_project_milestone2.model.Order
import com.divyanshoo.team10_mapd711_project_milestone2.model.OrderDao

class OrderRepository(private val orderDao: OrderDao) {

    suspend fun placeOrder(order: Order) {
        orderDao.insertOrder(order)
    }

    suspend fun getOrdersByCustomer(custId: Int): List<Order> {
        return orderDao.getOrdersByCustomer(custId)
    }

    suspend fun cancelOrder(orderId: Int) {
        orderDao.cancelOrder(orderId)
    }

    suspend fun markOrderAsDelivered(orderId: Int) {
        orderDao.markAsDelivered(orderId)
    }
}
