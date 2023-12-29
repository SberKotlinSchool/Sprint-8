package com.example.retailer.storage

import com.example.retailer.api.distributor.Item
import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class OrderStorageService(
        val orderRepository: OrderRepository
) : OrderStorage {

    companion object : KLogging()

    override fun createOrder(draftOrder: Order): PlaceOrderData {

        val orderEntity = OrderEntity(
                id = draftOrder.id ?: UUID.randomUUID().toString(),
                address = draftOrder.address,
                recipient = draftOrder.recipient,
                orderStatus = OrderStatus.SENT
        )
        val items = draftOrder.items.map { ItemEntity(id = it.id, name = it.name, orderEntity = orderEntity) }
        if (items.isNotEmpty()) {
            orderEntity.itemList = items as MutableList<ItemEntity>
        }
        orderRepository.save(orderEntity)

        val order = Order(orderEntity.id, orderEntity.address, orderEntity.recipient,
                items = orderEntity.itemList.map { Item(it.id, it.name) })
        logger.info { "Created new order id=${orderEntity.id}, status=${orderEntity.orderStatus}" }
        return PlaceOrderData(order, OrderInfo(orderId = orderEntity.id, status = orderEntity.orderStatus, signature = ""))
    }

    @Transactional
    override fun updateOrder(orderInfo: OrderInfo): Boolean {
        val orderEntityOptional = orderRepository.findById(orderInfo.orderId)
        return if (orderEntityOptional.isPresent) {
            val orderEntity = orderEntityOptional.get()
            logger.info { "Updating order id=${orderEntity.id} status from ${orderEntity.orderStatus} to ${orderInfo.status}" }
            orderEntity.orderStatus = orderInfo.status
            true
        } else {
            false
        }
    }

    override fun getOrderInfo(id: String): OrderInfo? {
        return orderRepository.findById(id)
                .map { OrderInfo(orderId = id, status = it.orderStatus, signature = "") }.orElse(null)
    }
}