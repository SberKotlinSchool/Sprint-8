package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

interface SystemListener {
    fun readResponse(order_info: OrderInfo)
}

@Component
class OrderInfoListener(@Autowired val orderService: OrderService) : SystemListener {

    @RabbitListener(queues = ["\${rabbitmq.queue}"])
    override fun readResponse(order_info: OrderInfo) {
        orderService.updateOrderInfo(order_info)
    }
}