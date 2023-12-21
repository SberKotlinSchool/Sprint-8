package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import com.example.retailer.repositories.OrderInfoRepository
import com.example.retailer.repositories.OrderRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class OrderStorageImpl(
    private val orderRepository: OrderRepository,
    private val orderInfoRepository: OrderInfoRepository
) : OrderStorage {
    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val order = orderRepository.save(draftOrder)
        val orderInfo = OrderInfo(order.id!!, OrderStatus.SENT, "Значение")
        orderInfoRepository.save(orderInfo)
        return PlaceOrderData(order, orderInfo)
    }

    override fun updateOrder(order: OrderInfo): Boolean {
        val findOrderInfo = orderInfoRepository.findById(order.orderId)

        return if (findOrderInfo.isPresent) {
            orderInfoRepository.save(order)
            true
        } else false
    }

    override fun getOrderInfo(id: String) = orderInfoRepository.findByIdOrNull(id)

}