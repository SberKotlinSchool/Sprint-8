package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import org.springframework.stereotype.Component

@Component
class OrderStorageImpl(
    private val orderRepository: OrderRepository,
    private val orderInfoRepository: OrderInfoRepository
) : OrderStorage {
    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val order = orderRepository.save(draftOrder)
        val orderInfo = orderInfoRepository.saveAndFlush(OrderInfo(order.id!!, OrderStatus.SENT))
        return PlaceOrderData(order, orderInfo)
    }

    override fun updateOrder(order: OrderInfo): Boolean =
        orderInfoRepository.runCatching {
            save(order)
        }.isSuccess

    override fun getOrderInfo(id: String): OrderInfo? =
        orderInfoRepository.findById(id).orElse(null)
}