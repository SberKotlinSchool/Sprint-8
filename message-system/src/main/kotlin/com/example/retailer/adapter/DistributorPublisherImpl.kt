package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class DistributorPublisherImpl(
    private val rabbitTemplate: RabbitTemplate,
    private val topicExchange: TopicExchange,
    @Value("\${consumer.routing.prefix}")
    private val consumerRoutingKeyPrefix: String,
    @Value("\${publisher.routing.format}")
    private val routingKeyFormat: String
) : DistributorPublisher {
    override fun placeOrder(order: Order): Boolean {
        return rabbitTemplate.runCatching {
            convertAndSend(topicExchange.name, routingKeyFormat.format(order.id), order) {
                it.messageProperties.headers["Notify-Exchange"] = topicExchange.name
                it.messageProperties.headers["Notify-RoutingKey"] = consumerRoutingKeyPrefix
                it
            }
        }.isSuccess
    }
}