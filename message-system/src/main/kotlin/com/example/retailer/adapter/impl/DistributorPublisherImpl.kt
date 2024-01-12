package com.example.retailer.adapter.impl

import com.example.retailer.adapter.DistributorPublisher
import com.example.retailer.api.distributor.Order
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class DistributorPublisherImpl(
    private val template: RabbitTemplate,
    private val topic: TopicExchange
) : DistributorPublisher {

    private val objectMapper = ObjectMapper()

    override fun placeOrder(order: Order): Boolean {
        val key = createKey(order)
        val data = order.toJson()
        return runCatching {
            template.convertAndSend(topic.name, key, data) { message ->
                message.messageProperties.headers["Notify-Exchange"] = "distributor_exchange"
                message.messageProperties.headers["Notify-RoutingKey"] = "retailer.dokl57"
                message
            }
            true
        }.getOrElse { false }
    }

    private fun createKey(order: Order): String =
        "distributor.placeOrder.dokl57.${order.id}"

    private fun Order.toJson(): String =
        objectMapper.writeValueAsString(this)
}