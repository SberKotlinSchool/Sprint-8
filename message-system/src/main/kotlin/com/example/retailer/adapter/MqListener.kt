package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo
import org.slf4j.LoggerFactory
import com.example.retailer.service.OrderService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
class MqListener {

    @Autowired
    private lateinit var orderService: OrderService

    @Autowired
    private lateinit var converter: Jackson2JsonMessageConverter

    @RabbitListener(queues = ["retailer.queue"])
    fun updateOrder(message: OrderInfo) {
        logger.info("Inbound message: {}", message)
        orderService.updateOrderInfo(message)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(MqListener::class.java)
    }
}
