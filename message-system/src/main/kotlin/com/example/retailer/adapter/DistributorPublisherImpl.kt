package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import com.example.retailer.config.RetailerConfig
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DistributorPublisherImpl : DistributorPublisher {

    @Autowired
    private lateinit var config: RetailerConfig

    @Autowired
    private var rabbitTemplate: RabbitTemplate? = null

    override fun placeOrder(order: Order): Boolean {

        try {
            val routingKey = "${config.outputRoutingKey}.${order.id}"

            val mapper = ObjectMapper().registerModule(KotlinModule())
            val json = mapper.writeValueAsString(order)

            rabbitTemplate?.convertAndSend(config.topicExchangeName, routingKey, json) { msg ->
                msg.messageProperties.headers["Notify-Exchange"] = config.topicExchangeName
                msg.messageProperties.headers["Notify-RoutingKey"] = "retailer.${config.githubUserName}"
                msg
            }

//            rabbitTemplate?.convertAndSend(routingKey, order) { msg ->
//                msg.messageProperties.headers["Notify-Exchange"] = "distributor_exchange"
//                msg.messageProperties.headers["Notify-RoutingKey"] = "retailer.${githubUsername}"
//                msg
//            }
        } catch (e: Exception) {
            return false
        }

        return true
    }
}