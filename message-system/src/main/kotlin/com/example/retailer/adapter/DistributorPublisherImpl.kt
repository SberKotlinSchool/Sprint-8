package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class DistributorPublisherImpl : DistributorPublisher {
    @Autowired
    private lateinit var rabbitTemplate: RabbitTemplate
    @Value("\${spring.rabbitmq.routingKey}")
    private lateinit var routingKey: String
    @Value("\${spring.rabbitmq.notifyRoutingKey}")
    private lateinit var notifyRoutingKey: String
    override fun placeOrder(order: Order): Boolean {
        requireNotNull(order.id){
            return false
        }
        rabbitTemplate.convertAndSend(routingKey + ".${order.id}", order) {
            it.messageProperties.headers["Notify-Exchange"] = rabbitTemplate.exchange
            it.messageProperties.headers["Notify-RoutingKey"] = notifyRoutingKey
            it
        }
        return true
    }
}