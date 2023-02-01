package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DistributorPublisherImpl(
    private val objectMapper: ObjectMapper,
    private val rabbitTemplate: RabbitTemplate,
) : DistributorPublisher {

    private val logger = LoggerFactory.getLogger(DistributorPublisherImpl::class.java)
    val user = "SirKot"

    override fun placeOrder(order: Order): Boolean {
        return if (order.id != null) {
            logger.info("Send order id = {}", order.id)
            objectMapper.writeValueAsString(order)
            rabbitTemplate.convertAndSend("distributor.placeOrder.${user}.${order.id}", order) {
                it.messageProperties.headers["Notify-Exchange"] = "distributor_exchange"
                it.messageProperties.headers["Notify-RoutingKey"] = "retailer.${user}"
                it
            }
            true
        } else {
            false
        }

    }
}