package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class DistributorPublisherImpl(
    private val template: RabbitTemplate,
    private val topic: TopicExchange
): DistributorPublisher {

    private val mapper = jacksonObjectMapper()

    override fun placeOrder(order: Order): Boolean {
        val orderJson = mapper.writeValueAsString(order)
        return if (order.id.isNullOrEmpty()) {
            template.convertAndSend(topic.name, "distributor.placeOrder.SEINBEVR.${order.id}", orderJson) {
                it.messageProperties.headers["Notify-Exchange"] = "distributor_exchange"
                it.messageProperties.headers["Notify-RoutingKey"] = "retailer.SEINBEVR"
                it
            }
            true
        } else {
            println("order id is null !")
            false
        }
    }
}