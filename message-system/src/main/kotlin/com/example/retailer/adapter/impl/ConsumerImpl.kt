package com.example.retailer.adapter.impl

import com.example.retailer.adapter.Consumer
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConsumerImpl: Consumer {

    @Autowired
    lateinit var orderService: OrderService

    @RabbitListener(queues = ["consumer"])
    override fun getResponse(message: String) {
        val orderInfo = jacksonObjectMapper().readValue(message, OrderInfo::class.java)
        orderService.updateOrderInfo(orderInfo)
    }
}