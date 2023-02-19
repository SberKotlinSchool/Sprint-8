package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import com.example.retailer.dao.InfoRepository
import com.example.retailer.dao.OrderRepository
import org.springframework.stereotype.Repository

@Repository
class OrderStorageImpl(
    private val orderRepository: OrderRepository,
    private val infoRepository: InfoRepository
) : OrderStorage {

    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val order = orderRepository.save(draftOrder)
        val info = infoRepository.saveAndFlush(OrderInfo(order.id!!, OrderStatus.SENT, ""))
        return PlaceOrderData(order, info)
    }

    override fun updateOrder(order: OrderInfo): Boolean {
        try {
            infoRepository.save(order)
        } catch (e: IllegalArgumentException) {
            return false
        }
        return true
    }

    override fun getOrderInfo(id: String): OrderInfo? {
        return infoRepository.findById(id).orElse(null)
    }
}