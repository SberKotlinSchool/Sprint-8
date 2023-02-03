package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class ConsumerImpl(private val orderService: OrderService) : Consumer {

    private val logger = LoggerFactory.getLogger(ConsumerImpl::class.java)

    @RabbitListener(queues = ["retailer_queue"])
    override fun receiveUpdate(mess: OrderInfo) {
        logger.info("Incoming message: {}", mess)
        orderService.updateOrderInfo(mess)
    }
}