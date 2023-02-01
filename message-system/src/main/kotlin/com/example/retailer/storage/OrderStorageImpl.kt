package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import com.example.retailer.repository.InfoRepo
import com.example.retailer.repository.OrderRepo
import org.springframework.stereotype.Repository

@Repository
class OrderStorageImpl(
    private val orderRepo: OrderRepo,
    private val infoRepo: InfoRepo
) : OrderStorage {

    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val order = orderRepo.save(draftOrder)
        val info = infoRepo.saveAndFlush(OrderInfo(order.id!!, OrderStatus.SENT, ""))
        return PlaceOrderData(order, info)
    }

    override fun updateOrder(order: OrderInfo): Boolean {
        try {
            infoRepo.save(order)
        } catch (e: IllegalArgumentException) {
            return false
        }
        return true
    }

    override fun getOrderInfo(id: String): OrderInfo? {
        return infoRepo.findById(id).orElse(null)
    }
}
