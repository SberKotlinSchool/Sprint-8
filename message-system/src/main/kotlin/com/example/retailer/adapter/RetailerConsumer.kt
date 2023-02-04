package com.example.retailer.adapter

import com.example.retailer.RETAILER_QUEUE_NAME
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

interface RetailerConsumer {

    fun consume(orderInfo: OrderInfo)
}

@Component
class RetailerConsumerImpl(private val orderService: OrderService) : RetailerConsumer {

    @RabbitListener(queues = [RETAILER_QUEUE_NAME])
    override fun consume(orderInfo: OrderInfo) {
        orderService.updateOrderInfo(orderInfo)
    }
}
