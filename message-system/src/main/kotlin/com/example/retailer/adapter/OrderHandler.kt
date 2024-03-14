package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
@EnableRabbit
class OrderHandler(
    private val orderService: OrderService
)  {

  @RabbitListener(queues = ["order.queue"])
  fun receiveNotify(order: OrderInfo) {
    orderService.updateOrderInfo(order)
  }
}