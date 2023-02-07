package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.storage.OrderStorage
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class RetailConsumerImpl(private val orderStorage: OrderStorage) : RetailConsumer {

    @RabbitListener(queues = ["#{queue.name}"])
    override fun updateOrderStatus(orderInfo: OrderInfo) {
        orderStorage.updateOrder(orderInfo)
    }
}