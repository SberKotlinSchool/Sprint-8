package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

/**
 * Интерфейс для получения обновлений от дистрибьютора
 */

@Component
class RetailerConsumerImpl(private val orderService: OrderService) : RetailerConsumer {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @RabbitListener(queues = ["\${retailer.queue}"])
    override fun updateOrder(orderInfo: OrderInfo) {
        runCatching { orderService.updateOrderInfo(orderInfo) }
                .onSuccess { logger.info("Received message $orderInfo") }
                .onFailure { ex ->
                    logger.error(ex.localizedMessage)
                }
    }
}