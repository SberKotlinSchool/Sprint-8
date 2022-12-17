package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import com.example.retailer.storage.Repositories.OrderInfoRepository
import com.example.retailer.storage.Repositories.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class OrderStorageImpl : OrderStorage {

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @Autowired
    private lateinit var orderInfoRepository: OrderInfoRepository

    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val order = orderRepository.save(draftOrder)
        val info = orderInfoRepository.save(
            OrderInfo(draftOrder.id!!, OrderStatus.SENT, "signature")
        )

        return PlaceOrderData(order, info)
    }

    override fun updateOrder(order: OrderInfo): Boolean {
        return try {
            orderInfoRepository.save(order)
            true
        } catch (exc: Exception) {
            println(exc.message)
            println(exc.stackTrace)
            false
        }
    }

    override fun getOrderInfo(id: String): OrderInfo? {
        return orderInfoRepository.findByIdOrNull(id)
    }

}