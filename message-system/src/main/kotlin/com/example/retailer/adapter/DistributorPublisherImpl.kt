package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import mu.KotlinLogging
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class DistributorPublisherImpl(
    private val template: RabbitTemplate,
) : DistributorPublisher {

    override fun placeOrder(order: Order): Boolean {
        if (order.id != null) {
            try {
                val routingKey = "distributor.placeOrder.ctacshamsheev.${order.id}"
                template.convertAndSend(template.exchange, routingKey, order) {
                    it.messageProperties.headers["Notify-Exchange"] = template.exchange
                    it.messageProperties.headers["Notify-RoutingKey"] = "retailer.ctacshamsheev"
                    it
                }
                return true
            } catch (e: Exception) {
                log.error { e.message }
            }
        } else {
            log.error { "order.id is null" }
        }
        return false
    }


    companion object {
        val log = KotlinLogging.logger {}
    }
}