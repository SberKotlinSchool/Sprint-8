package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import com.example.retailer.config.RabbitProperties
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service


@Service
class DistributorPublisherImpl(
    private val rabbitTemplate: RabbitTemplate,
    private val rabbitProperties: RabbitProperties
) : DistributorPublisher {

    override fun placeOrder(order: Order) = if (order.id != null) {
        rabbitTemplate.convertAndSend(rabbitProperties.routingKey + ".${order.id}", order) {
            it.messageProperties.headers["Notify-Exchange"] = rabbitTemplate.exchange
            it.messageProperties.headers["Notify-RoutingKey"] = rabbitProperties.notifyRoutingKey
            it
        }
        true
    } else {
        false
    }

}