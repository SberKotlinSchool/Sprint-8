package com.example.retailer.publisher

import com.example.retailer.adapter.DistributorPublisher
import com.example.retailer.api.distributor.Order
import com.fasterxml.jackson.databind.ObjectMapper
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
        try {
            val objectMapper = ObjectMapper()

            val message = objectMapper.writeValueAsString(order)

            rabbitTemplate.convertAndSend(
                topicExchange.name,
                "distributor.placeOrder.alexeyRozhok.${order.id}",
                message
            ) { message ->

                message.messageProperties.headers["Notify-Exchange"] = "distributor_exchange"
                message.messageProperties.headers["Notify-RoutingKey"] = "retailer.alexeyRozhok"
                message
            }
            return true
        } catch (exc: Exception) {
            println("failed to save order")
            println(exc.stackTrace)
            return false
        }
    }
}