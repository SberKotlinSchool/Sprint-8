package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import org.slf4j.LoggerFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
class DistributorPublisherImpl : DistributorPublisher {

    @Autowired
    private lateinit var rabbitTemplate: RabbitTemplate

    @Autowired
    private lateinit var topicExchange: TopicExchange

    override fun placeOrder(order: Order): Boolean {
        if (order.id == null) {
            return false
        }
        val objectMapper = ObjectMapper()

        val message = objectMapper.writeValueAsString(order)
        logger.info("Outbound message : $message" )
        rabbitTemplate.convertAndSend(
            topicExchange.name,
            "distributor.placeOrder.kulinichrs.${order.id}",
            message
        ) { message ->

            message.messageProperties.headers["Notify-Exchange"] = "distributor_exchange"
            message.messageProperties.headers["Notify-RoutingKey"] = "retailer.kulinichrs"
            message
        }
        return true
    }

    companion object {
        private val logger = LoggerFactory.getLogger(DistributorPublisherImpl::class.java)
    }
}