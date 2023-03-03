package com.example.retailer.storage.impl

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import com.example.retailer.repo.OrderInfoRepository
import com.example.retailer.repo.OrderRepository
import com.example.retailer.storage.OrderStorage
import com.example.retailer.storage.PlaceOrderData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderStorageImpl(@Autowired val orderInfoRepository: OrderInfoRepository,
                       @Autowired val orderRepository: OrderRepository
) : OrderStorage {

    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val order = orderRepository.save(draftOrder)
        val orderInfo = OrderInfo(order.id!!, OrderStatus.SENT, "")
        orderInfoRepository.save(orderInfo)
        return PlaceOrderData(order, orderInfo)
    }

    override fun updateOrder(order: OrderInfo): Boolean {
        val orderInfo = orderInfoRepository.findById(order.orderId)
        return if (orderInfo.isPresent) {
            orderInfoRepository.save(order)
            true
        } else false
    }

    override fun getOrderInfo(id: String): OrderInfo? {
        return orderInfoRepository.findById(id).orElse(null)
    }
}