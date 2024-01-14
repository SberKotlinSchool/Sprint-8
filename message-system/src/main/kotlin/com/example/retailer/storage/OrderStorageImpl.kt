package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import com.example.retailer.repository.OrderInfoRepository
import com.example.retailer.repository.OrderRepository
import java.util.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class OrderStorageImpl(
    private val orderRepository: OrderRepository,
    private val orderInfoRepository: OrderInfoRepository
) : OrderStorage {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }

    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val order = orderRepository.save(draftOrder)
        val orderInfo = orderInfoRepository.save(OrderInfo(order.id!!, OrderStatus.SENT, UUID.randomUUID().toString()))
        return PlaceOrderData(order, orderInfo)
    }

    override fun updateOrder(order: OrderInfo): Boolean =
        runCatching { orderInfoRepository.save(order) }
            .onFailure { ex -> logger.error("updateOrder: $ex.localizedMessage") }
            .isSuccess

    override fun getOrderInfo(id: String): OrderInfo? =
        orderInfoRepository.findById(id).orElse(null)

}
