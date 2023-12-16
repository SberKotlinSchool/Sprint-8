package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class DistributorConsumerImpl(private var orderService: OrderService) : DistributorConsumer {

    @RabbitListener(queues = ["\${rabbitmq.queue}"])
    override fun updateOrderInfo(orderInfo: OrderInfo) {
        orderService.updateOrderInfo(orderInfo)
    }
}