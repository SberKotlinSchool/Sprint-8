package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DistributorConsumerImpl(
    @Autowired
    private var orderService: OrderService
) : DistributorConsumer {

    @RabbitListener(queues = ["\${rabbitmq.queue}"])
    override fun getOrderUpdate(orderInfo: OrderInfo) {
        orderService.updateOrderInfo(orderInfo)
    }
}