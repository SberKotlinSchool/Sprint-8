package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class DistributorPublisherImpl(
    @Autowired private val rabbitTemplate: RabbitTemplate,
    @Value("\${spring.rabbitmq.routingKey}") private val routingKey: String,
    @Value("\${spring.rabbitmq.notifyRoutingKey}") private val notifyRoutingKey: String
): DistributorPublisher{

    override fun placeOrder(order: Order) : Boolean {
        return if (order.id != null) {
            rabbitTemplate.convertAndSend(routingKey + ".${order.id}", order) {
                it.messageProperties.headers["Notify-Exchange"] = rabbitTemplate.exchange
                it.messageProperties.headers["Notify-RoutingKey"] = notifyRoutingKey
                it
            }
            true
        } else {
            false
        }
    }

}