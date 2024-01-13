package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
class DistributorPublisherImpl(
    private val template: RabbitTemplate,
    private val exchange: TopicExchange,
    @Value("\${rabbitmq.consumer.routing.key}")
    private val consumerRoutingKey: String,
    @Value("\${rabbitmq.publisher.routing.key}")
    private val publisherRoutingKeyMask: String
) : DistributorPublisher {
    override fun placeOrder(order: Order): Boolean {
        val publisherRoutingKey = publisherRoutingKeyMask.format(order.id)
        try {
            template.convertAndSend(exchange.name, publisherRoutingKey, order) {
                it.messageProperties.headers["Notify-Exchange"] = exchange.name
                it.messageProperties.headers["Notify-RoutingKey"] = consumerRoutingKey
                it
            }
        } catch (e: Exception) {
            return false
        }
        return true
    }
}
