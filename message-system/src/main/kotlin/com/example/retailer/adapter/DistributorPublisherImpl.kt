package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class DistributorPublisherImpl(
    private val objectMapper: ObjectMapper,
    private val rabbitTemplate: RabbitTemplate,
) : DistributorPublisher {

    companion object {
        private val logger = LoggerFactory.getLogger(DistributorPublisherImpl::class.java)
    }

    override fun placeOrder(order: Order): Boolean {
        return if (order.id != null) {
            logger.info("Размещение заказа id = {}", order.id)
            val message = objectMapper.writeValueAsString(order)
            rabbitTemplate.convertAndSend("distributor.placeOrder.Michael.${order.id}", order) {
                it.messageProperties.headers["Notify-Exchange"] = "distributor_exchange"
                it.messageProperties.headers["Notify-RoutingKey"] = "retailer.Michael"
                it
            }
            true
        } else {
            false
        }
    }
}