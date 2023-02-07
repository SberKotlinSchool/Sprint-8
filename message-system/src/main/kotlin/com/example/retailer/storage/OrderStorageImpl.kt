package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import org.springframework.stereotype.Component
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Component
class OrderStorageImpl: OrderStorage {

    private val storage = ConcurrentHashMap<String, PlaceOrderData>()

    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val order = Order(draftOrder.id ?: UUID.randomUUID().toString().replace("-",""),
            draftOrder.address, draftOrder.recipient, draftOrder.items)
        val placeOrderData = PlaceOrderData(order, OrderInfo(order.id!!, OrderStatus.SENT, ""))
        storage[order.id] = placeOrderData
        return placeOrderData
    }

    override fun updateOrder(order: OrderInfo): Boolean =
        storage[order.orderId]?.let {
            it.info.status = order.status
            true
        } ?: false

    override fun getOrderInfo(id: String): OrderInfo? = storage[id]?.info
}