package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


interface DistributorListener {
    fun onOrderInfo(orderInfo: OrderInfo)
}

@Component
class DistributorListenerImpl @Autowired constructor
    (private val orderService: OrderService) : DistributorListener {

    @RabbitListener(queues = ["\${rabbitmq.queue}"])
    override fun onOrderInfo(orderInfo: OrderInfo) {
        orderService.updateOrderInfo(orderInfo)
    }
}