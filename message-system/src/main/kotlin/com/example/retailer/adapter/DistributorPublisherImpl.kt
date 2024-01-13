package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class DistributorPublisherImpl(
    private val template: RabbitTemplate,
) : DistributorPublisher {

    override fun placeOrder(order: Order): Boolean {
        return try {
            template.convertAndSend(
                template.exchange,
                "distributor.placeOrder.BeanDelegation.${order.id}",
                order
            ) {
                it.messageProperties.headers["Notify-Exchange"] = template.exchange
                it.messageProperties.headers["Notify-RoutingKey"] = "retailer.BeanDelegation"
                it
            }
            true
        } catch (exception: Exception) {
            false
        }
    }
}