package com.example.retailer.adapter.impl

import com.example.retailer.adapter.RetailerConsumer
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class RetailerConsumerImpl(
    private val orderService: OrderService
) : RetailerConsumer {

    @RabbitListener(queues = ["#{queue.name}"])
    override fun receiveNotify(order: OrderInfo) {
        orderService.updateOrderInfo(order)
    }
}