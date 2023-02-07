package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ConsumerImpl: Consumer {

    @Autowired
    private lateinit var orderService: OrderService

    private val mapper = ObjectMapper();

    @RabbitListener(queues = ["store"])
    override fun updateOrder(message: String) {
        try {
            val orderInfo = mapper.readValue(message, OrderInfo::class.java)
            orderService.updateOrderInfo(orderInfo)
        } catch (e: Exception)
        {
            println("Ошибка обновления заказа")
        }
    }
}