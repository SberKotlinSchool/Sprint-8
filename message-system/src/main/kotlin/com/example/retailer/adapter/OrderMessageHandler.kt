package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.amqp.core.Message

@EnableRabbit
@Component
class OrderMessageHandler(
    private val orderService: OrderService,
    private val objectMapper: ObjectMapper
) {
    @RabbitListener(
        queues = ["order.queue"]
    )
    public fun onMessage(message: Message) {
        val orderInfo = objectMapper.readValue<OrderInfo>(message.body)
        orderService.updateOrderInfo(orderInfo)
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}