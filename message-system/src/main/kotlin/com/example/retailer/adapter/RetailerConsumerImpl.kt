package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RetailerConsumerImpl : RetailerConsumer {

    val log = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var orderService: OrderService

    @RabbitListener(queues = ["retailer_queue"])
    override fun listen(msg: Message) {

        log.info("Received raw message: {}", msg)

        val json = String(msg.body)

        val orderInfo = objectMapper.readValue(json, OrderInfo::class.java)

        orderService.updateOrderInfo(orderInfo)
    }
}