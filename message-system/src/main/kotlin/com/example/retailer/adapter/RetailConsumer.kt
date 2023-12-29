package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.storage.OrderStorage
import mu.KLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class RetailConsumer(private val orderStorage: OrderStorage) {

    companion object : KLogging()

    /**
     * Слушатель сообщений из RabbitMQ по имени очереди
     */
    @RabbitListener(queues = ["DistributorRetailQueue"])
    fun consume(orderInfo: OrderInfo) {
        logger.info { "Consume orderInfo $orderInfo" }

        orderStorage.updateOrder(orderInfo)
    }
}
