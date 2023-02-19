package com.example.retailer.adapter.impl

import com.example.retailer.adapter.DistributorPublisher
import com.example.retailer.api.distributor.Order
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class DistributorPublisherImpl(
    private val template: RabbitTemplate,
    private val exchange: TopicExchange,
    @Value("\${consumer.routing.prefix}")
    private val consumerRoutingKeyPrefix: String,
    @Value("\${publisher.routing.format}")
    private val routingKeyFormat: String
) : DistributorPublisher {

    override fun placeOrder(order: Order): Boolean {
        val routingKey = routingKeyFormat.format(order.id)
        try {
            template.convertAndSend(exchange.name, routingKey, order) {
                it.messageProperties.headers["Notify-Exchange"] = exchange.name
                it.messageProperties.headers["Notify-RoutingKey"] = consumerRoutingKeyPrefix
                it
            }
        } catch (e: Exception) {
            return false
        }
        return true
    }
}