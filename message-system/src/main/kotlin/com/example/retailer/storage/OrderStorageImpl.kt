package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderStorageImpl(
    @Autowired val orderRepo: OrderRepo,
    @Autowired val orderInfoRepo: OrderInfoRepo) : OrderStorage {

    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val order = orderRepo.save(draftOrder)
        val info = orderInfoRepo.save(OrderInfo(order.id!!, OrderStatus.SENT, ""))
        return PlaceOrderData(order, info)
    }

    override fun updateOrder(order: OrderInfo): Boolean {
        var result = false
        orderRepo.findById(order.orderId).ifPresent {
            orderInfoRepo.save(order)
            result = true
        }
        return result
    }

    override fun getOrderInfo(id: String): OrderInfo? = orderInfoRepo.findById(id).orElse(null)
}