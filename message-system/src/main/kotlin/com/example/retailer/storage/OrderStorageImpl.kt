package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class OrderStorageImpl : OrderStorage {

    @Autowired
    lateinit var orderInfoRepository: OrderInfoRepository

    @Autowired
    lateinit var orderRepository: OrderRepository

    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val order = orderRepository.save(draftOrder)
        val orderInfo = orderInfoRepository.save(OrderInfo(order.id!!, OrderStatus.SENT, ""))
        return PlaceOrderData(order, orderInfo)
    }

    override fun updateOrder(order: OrderInfo) : Boolean {
        try {
            orderInfoRepository.save(order)
            return true
        } catch (e: Exception) {
            println("Error while updating order ${e}")
            return false
        }

    }

    override fun getOrderInfo(id: String): OrderInfo? {
        return orderInfoRepository.findByIdOrNull(id)
    }


}