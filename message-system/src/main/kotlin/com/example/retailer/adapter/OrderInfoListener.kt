package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class OrderInfoListener(val orderService: OrderService)  {

    @RabbitListener(queues = ["\${rabbitmq.queue}"])
    fun readResponse(message: OrderInfo) {
        orderService.updateOrderInfo(message)
    }
}