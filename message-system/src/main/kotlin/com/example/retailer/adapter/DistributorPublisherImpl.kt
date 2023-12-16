package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
class DistributorPublisherImpl(private val rabbitTemplate: RabbitTemplate, private val exchange: TopicExchange) :
    DistributorPublisher {

    companion object {
        const val HEADER_EXCHANGE = "Notify-Exchange"
        const val HEADER_ROUTING_KEY = "Notify-RoutingKey"
    }

    @Value("\${rabbitmq.producer.routing.key")
    lateinit var producerRoutingKey: String

    @Value("\${rabbitmq.consumer.routing.key}")
    lateinit var consumerRoutingKey: String


    override fun placeOrder(order: Order): Boolean {
        try {
            rabbitTemplate.convertAndSend(exchange.name, String.format(producerRoutingKey, order.id), order) {
                it.messageProperties.headers[HEADER_EXCHANGE] = exchange.name
                it.messageProperties.headers[HEADER_ROUTING_KEY] = consumerRoutingKey
                it
            }
        } catch (e: Exception) {
            return false
        }
        return true
    }
}