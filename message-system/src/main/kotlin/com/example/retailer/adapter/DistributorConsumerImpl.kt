package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class DistributorConsumerImpl(private val orderService: OrderService) : DistributorConsumer {
    @RabbitListener(queues = ["retailer_queue"])
    override fun receiveUpdate(orderInfo: OrderInfo) {
        orderService.updateOrderInfo(orderInfo)
    }
}