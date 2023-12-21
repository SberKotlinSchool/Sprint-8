package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service


@Service
class RetailerConsumerImpl(
    private val orderService: OrderService
) : RetailerConsumer {
    private val objectMapper = ObjectMapper()
    @RabbitListener(queues = ["retailer.queue"])
    override fun receive(message: String) {
        val orderInfo = objectMapper.readValue(message, OrderInfo::class.java)
        orderService.updateOrderInfo(orderInfo)
    }
}