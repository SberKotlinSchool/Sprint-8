package com.example.retailer.adapter.impl

import com.example.retailer.adapter.DistributorPublisher
import com.example.retailer.api.distributor.Order
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DistributorPublisherImpl: DistributorPublisher {

    @Autowired
    lateinit var rabbitTemplate: RabbitTemplate

    @Autowired
    lateinit var topicExchange: TopicExchange

    override fun placeOrder(order: Order): Boolean {

        val id = order.id
        if (id.isNullOrEmpty()) return false

        val output = jacksonObjectMapper().writeValueAsString(order)

        rabbitTemplate.convertAndSend(topicExchange.name, "distributor.placeOrder.Goshiq.$id", output) {
            it.messageProperties.headers["Notify-Exchange"] = "distributor_exchange"
            it.messageProperties.headers["Notify-RoutingKey"] = "retailer.Goshiq"
            it
        }

        return true
    }

}