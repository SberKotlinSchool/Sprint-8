package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class DistributorPublisherImpl(
    private val template: RabbitTemplate,
) : DistributorPublisher {
    private val logger = LoggerFactory.getLogger(DistributorPublisherImpl::class.java)

    override fun placeOrder(order: Order): Boolean {
        if (order.id == null) {
            logger.info("placeOrder(): ORDER ID IS NULL")
            return false
        }
        return try {
            template.convertAndSend(
                template.exchange,
                "distributor.placeOrder.firebat2.${order.id}",
                order
            ) {
                it.messageProperties.headers["Notify-Exchange"] = template.exchange
                it.messageProperties.headers["Notify-RoutingKey"] = "retailer.firebat2"
                it
            }
            true
        } catch (exception: Exception) {
            logger.info("placeOrder(): ${exception.stackTrace}")
            false
        }
    }
}