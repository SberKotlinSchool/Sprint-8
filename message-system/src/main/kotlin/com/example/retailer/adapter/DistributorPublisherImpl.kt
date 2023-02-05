package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DistributorPublisherImpl: DistributorPublisher {

    @Autowired
    private lateinit var template: RabbitTemplate

    @Autowired
    private lateinit var exchange: TopicExchange

    override fun placeOrder(order: Order): Boolean {

        try {
            template.convertAndSend(exchange.name, "distributor.placeOrder.Michael.${order.id}", order) {
                it.messageProperties.headers["Notify-Exchange"] = exchange.name
                it.messageProperties.headers["Notify-RoutingKey"] = "retailer.Michael"
                it
            }
        } catch (e: Exception) {
            return false
        }
        return true
    }
}