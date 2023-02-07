package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import com.example.retailer.config.RabbitMqConfiguration.Companion.NOTIFY_EXCHANGE
import com.example.retailer.config.RabbitMqConfiguration.Companion.NOTIFY_ROUTING_KEY
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
class DistributorPublisherImpl(private val rabbitTemplate: RabbitTemplate) : DistributorPublisher {

    @Value("\${distributor.routing.key}")
    private lateinit var distributorRoutingKey: String
    @Value("\${retailer.routing.key}")
    private lateinit var retailerRoutingKey: String

    override fun placeOrder(order: Order): Boolean =
        if (order.id != null) {
            rabbitTemplate.convertAndSend("$distributorRoutingKey.${order.id}", order) {
                it.messageProperties.headers[NOTIFY_EXCHANGE] = rabbitTemplate.exchange
                it.messageProperties.headers[NOTIFY_ROUTING_KEY] = retailerRoutingKey
                it
            }
            true
        } else {
            false
        }
}