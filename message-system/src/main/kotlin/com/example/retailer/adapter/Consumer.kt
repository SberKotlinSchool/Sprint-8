package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Consumer {
    @Autowired
    private lateinit var orderService: OrderService

    @RabbitListener(queues = ["consumer"])
    fun receiveUpdate(massage: String) {
        val mapper = jacksonObjectMapper()
        val orderInfo = mapper.readValue(massage, OrderInfo::class.java)
        orderService.updateOrderInfo(orderInfo)
    }
}