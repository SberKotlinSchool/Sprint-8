package com.example.retailer.storage.impl

import com.example.retailer.repository.OrderInfoRepository
import com.example.retailer.repository.OrderRepository
import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import com.example.retailer.storage.OrderStorage
import com.example.retailer.storage.PlaceOrderData
import org.springframework.stereotype.Service

@Service
class OrderStorageImpl(
    private val orderRepository: OrderRepository,
    private val orderInfoRepository: OrderInfoRepository,
) : OrderStorage {

    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val order = orderRepository.save(draftOrder)
        order.id?.let {
            val draftOrderInfo = OrderInfo(it, OrderStatus.SENT, "")
            val orderInfo = orderInfoRepository.save(draftOrderInfo)
            return PlaceOrderData(order, orderInfo)
        } ?: throw IllegalArgumentException("Error. Order.id is null")
    }

    override fun updateOrder(order: OrderInfo): Boolean {
        return if (orderInfoRepository.existsById(order.orderId)) {
            orderInfoRepository.save(order)
            true
        } else {
            false
        }
    }

    override fun getOrderInfo(id: String): OrderInfo? {
        return orderInfoRepository.findById(id).orElseGet { null }
    }
}
