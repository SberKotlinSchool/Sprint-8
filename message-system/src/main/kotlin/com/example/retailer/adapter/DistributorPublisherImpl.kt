package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import com.example.retailer.config.RetailerConfig
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DistributorPublisherImpl : DistributorPublisher {

    val log = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var config: RetailerConfig

    @Autowired
    private var rabbitTemplate: RabbitTemplate? = null

    override fun placeOrder(order: Order): Boolean {

        log.info("place order: {}", order)

        try {
            val routingKey = "${config.outputRoutingKey}.${order.id}"

            val json = objectMapper.writeValueAsString(order)

            rabbitTemplate?.convertAndSend(config.topicExchangeName, routingKey, json) { msg ->
                msg.messageProperties.headers["Notify-Exchange"] = config.topicExchangeName
                msg.messageProperties.headers["Notify-RoutingKey"] = config.inputRoutingKey
                msg
            }

        } catch (e: Exception) {
            return false
        }

        return true
    }
}