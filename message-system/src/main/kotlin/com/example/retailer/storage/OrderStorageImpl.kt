package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import com.example.retailer.repository.OrderInfoRepository
import com.example.retailer.repository.OrderRepository
import org.springframework.stereotype.Repository

@Repository
class OrderStorageImpl(
    private val orderRepository: OrderRepository,
    private val orderInfoRepository: OrderInfoRepository
) : OrderStorage {
    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val order = orderRepository.save(draftOrder)
        val info = orderInfoRepository.saveAndFlush(OrderInfo(order.id!!, OrderStatus.SENT, ""))
        return PlaceOrderData(order, info)
    }

    override fun updateOrder(orderInfo: OrderInfo): Boolean {
        try {
            orderInfoRepository.save(orderInfo)
        } catch (e: IllegalArgumentException) {
            return false
        }
        return true
    }

    override fun getOrderInfo(id: String): OrderInfo? {
        return orderInfoRepository.findById(id).orElse(null)
    }
}