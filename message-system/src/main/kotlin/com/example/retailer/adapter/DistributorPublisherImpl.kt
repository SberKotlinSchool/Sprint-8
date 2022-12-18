package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DistributorPublisherImpl : DistributorPublisher {
    @Autowired
    private lateinit var rabbitTemplate: RabbitTemplate

    @Autowired
    private lateinit var topicExchange: TopicExchange

    override fun placeOrder(order: Order): Boolean {

        val userName = "gaponchukag"
        val orderMapper = jacksonObjectMapper()
        val msg = orderMapper.writeValueAsString(order)

        return try {

            rabbitTemplate.convertAndSend(topicExchange.name, "distributor.placeOrder.$userName.${order.id}", msg) {
                it.messageProperties.headers["Notify-Exchange"] = "distributor_exchange"
                it.messageProperties.headers["Notify-RoutingKey"] = "retailer.$userName"
                it
            }

            true

        } catch (e: Exception) {
            false
        }
    }
}