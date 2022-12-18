package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DistributorPublisherImpl : DistributorPublisher {
    @Autowired
    private lateinit var template: RabbitTemplate

    @Autowired
    private lateinit var topic: TopicExchange

    override fun placeOrder(order: Order): Boolean {
        if (order.id == null) {
            return false
        }
        val mapper = jacksonObjectMapper()
        val message = mapper.writeValueAsString(order)

        template.convertAndSend(topic.name, "distributor.placeOrder.PavelPopov4Kotlin.${order.id}", message) {
            it.messageProperties.headers["Notify-Exchange"] = "distributor_exchange"
            it.messageProperties.headers["Notify-RoutingKey"] = "retailer.PavelPopov4Kotlin"
            it
        }
        return true

    }
}