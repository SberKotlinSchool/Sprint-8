package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class DistributorPublisherImpl(@Autowired private val rabbitTemplate: RabbitTemplate,
                               @Autowired private val objectMapper: ObjectMapper) : DistributorPublisher {


    @Value("\${rabbitmq.queue.name}")
    private val queue: String? = null

    @Value("\${rabbitmq.exchange.name}")
    private val exchange: String? = null

    @Value("\${rabbitmq.routing.key}")
    private val routingKey: String? = null

    override fun placeOrder(order: Order): Boolean {
        return try {
            rabbitTemplate.convertAndSend(
                exchange!!,
                "$routingKey.${order.id}",
                objectMapper.writeValueAsString(order)
            ){
                it.messageProperties.headers["Notify-Exchange"] = queue
                it.messageProperties.headers["Notify-RoutingKey"] = routingKey
                it
            }
            true
        } catch (ex: Exception) {
            false
        }
    }
}