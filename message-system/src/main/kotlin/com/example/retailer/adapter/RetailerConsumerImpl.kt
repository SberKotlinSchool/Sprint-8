package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class RetailerConsumerImpl(
    private val orderService: OrderService
) : RetailerConsumer {

    companion object {
        private val logger = LoggerFactory.getLogger(RetailerConsumerImpl::class.java)
    }

    @RabbitListener(queues = ["retailer_queue"])
    override fun updateOrder(message: OrderInfo) {
        logger.info("Входящее сообщение: {}", message)
        orderService.updateOrderInfo(message)
    }
}