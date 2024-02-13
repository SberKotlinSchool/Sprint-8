package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class DistributorPublisherImpl(
    private val rabbitTemplate: RabbitTemplate
) : DistributorPublisher {
  override fun placeOrder(order: Order): Boolean {
    try {
      rabbitTemplate.setExchange("distributor_exchange")
      rabbitTemplate.convertAndSend("distributor.placeOrder.IamNotUrKitty.${order.id}", order) {
        it.messageProperties.headers["Notify-Exchange"] = "distributor_exchange"
        it.messageProperties.headers["Notify-RoutingKey"] = "retailer.IamNotUrKitty"
        it
      }
    } catch (e: Exception) {
      return false
    }
    return true
  }
}