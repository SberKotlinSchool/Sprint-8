package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import com.example.retailer.config.RabbitMQConfig
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class DistributorPublisherImpl(
    @Autowired private var rabbitMQConfig: RabbitMQConfig,
    @Autowired private var rabbitTemplate: AmqpTemplate
) : DistributorPublisher {
    override fun placeOrder(order: Order): Boolean {
        return try {
            rabbitTemplate.convertAndSend(rabbitMQConfig.getRoutingkey() + "." + order.id, order)
            {
                it.messageProperties.headers["Notify-Exchange"] = rabbitMQConfig.getExchange()
                it.messageProperties.headers["Notify-RoutingKey"] = rabbitMQConfig.getNotifyRoutingKey()
                it
            }

            true
        } catch (e: Exception) {
            false
        }
    }
}