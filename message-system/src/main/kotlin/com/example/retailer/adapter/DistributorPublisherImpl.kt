package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class DistributorPublisherImpl(
    private val template: RabbitTemplate,
    private val topic: TopicExchange
) : DistributorPublisher {
    private val objectMapper = ObjectMapper()
    override fun placeOrder(order: Order) = order.id?.let {
        val key = "distributor.placeOrder.ShedowSith.${order.id}"
        val data = objectMapper.writeValueAsString(order)
        template.convertAndSend(topic.name, key, data) { message ->
            message.messageProperties.headers["Notify-Exchange"] = "distributor_exchange"
            message.messageProperties.headers["Notify-RoutingKey"] = "retailer.ShedowSith"
            message
        }
        true
    } ?: false
}