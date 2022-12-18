package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ConsumerImpl : Consumer {
    @Autowired
    private lateinit var orderService: OrderService

    @RabbitListener(queues = ["consumer"])
    override fun receiveUpdate(massage: String) {
        val orderInfoMapper = jacksonObjectMapper()
        val orderInfo = orderInfoMapper.readValue(massage, OrderInfo::class.java)
        orderService.updateOrderInfo(orderInfo)
    }

}