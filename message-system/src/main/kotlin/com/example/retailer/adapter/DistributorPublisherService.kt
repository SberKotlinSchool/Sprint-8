package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import mu.KLogging
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service


@Service
class DistributorPublisherService(
        val rabbitTemplate: RabbitTemplate
) : DistributorPublisher {

    companion object : KLogging()

    @Value("\${messages.exchange.routingKey}")
    private lateinit var distributorRoutingKey: String

    @Value("\${messages.exchange.notify-routingkey}")
    private lateinit var notifyRoutingKeyHeader: String

    @Value("\${messages.exchange.name}")
    private lateinit var exchangeName: String

    /**
     * Отправляет заказ в distributor через AMQP
     */
    override fun placeOrder(order: Order): Boolean =
            if (order.id != null) {
                val routingKey = distributorRoutingKey.replace("{orderId}", order.id)
                rabbitTemplate.convertAndSend(routingKey, order) {
                    it.messageProperties.headers["Notify-Exchange"] = exchangeName
                    it.messageProperties.headers["Notify-RoutingKey"] = notifyRoutingKeyHeader
                    it
                }
                logger.info { "Successfully sent order id=${order.id}" }
                true
            } else {
                false
            }
}