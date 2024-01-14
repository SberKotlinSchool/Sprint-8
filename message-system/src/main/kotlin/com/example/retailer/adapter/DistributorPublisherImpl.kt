package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import mu.KLogging
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class DistributorPublisherImpl(
    private val orderRabbitTemplate: RabbitTemplate
) : DistributorPublisher {
    override fun placeOrder(order: Order): Boolean = runCatching {
        orderRabbitTemplate.convertAndSend("distributor.placeOrder.AntonyKon.${order.id}", order)
        true
    }
        .onFailure {
            logger.error("При отправке заказа с ${order.id} произошла ошибка", it)
        }
        .getOrElse { false }

    companion object : KLogging()
}