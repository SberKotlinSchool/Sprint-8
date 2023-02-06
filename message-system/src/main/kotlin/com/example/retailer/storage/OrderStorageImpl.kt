package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import com.example.retailer.repository.OrderInfoRepository
import com.example.retailer.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class OrderStorageImpl(@Autowired val orderRepo: OrderRepository, @Autowired val orderInfoRepo: OrderInfoRepository) : OrderStorage {

    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val order = orderRepo.save(draftOrder)
        val orderInfo = orderInfoRepo.save(OrderInfo(orderId = order.id, status = OrderStatus.SENT, signature = ""))
        return PlaceOrderData(order, orderInfo)
    }

    override fun updateOrder(orderInfo: OrderInfo): Boolean {
        return try {
            orderInfoRepo.save(orderInfo)
            true
        } catch (ex: Exception) {
            false
        }
    }

    override fun getOrderInfo(id: String): OrderInfo? {
        return orderInfoRepo.findByIdOrNull(id)
    }

}