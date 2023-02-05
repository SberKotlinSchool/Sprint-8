package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import com.example.retailer.repository.OrderInfoRepository
import com.example.retailer.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderStorageImpl: OrderStorage {

    @Autowired
    private lateinit var orderRepo: OrderRepository

    @Autowired
    private lateinit var orderInfoRepo: OrderInfoRepository

    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val order = orderRepo.save(draftOrder)
        val info = orderInfoRepo.save(OrderInfo(order.id!!, OrderStatus.SENT, ""))
        return PlaceOrderData(order, info)
    }

    override fun updateOrder(order: OrderInfo): Boolean {
        val orderInfo = getOrderInfo(order.orderId)
        return if (orderInfo != null) {
            orderInfoRepo.save(order)
            true
        } else false
    }

    override fun getOrderInfo(id: String): OrderInfo? {
        return orderInfoRepo.findById(id).orElse(null)
    }
}