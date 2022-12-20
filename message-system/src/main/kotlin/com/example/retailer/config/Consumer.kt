package com.example.retailer.config

import com.example.retailer.service.OrderService
import com.example.retailer.mapper.RetailMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Consumer {

    @Autowired
    lateinit var orderService: OrderService

    @RabbitListener(queues = ["retailer_queue"])
    fun retailer(message : String){
        LOGGER.info(" + consumer + got message : $message")
        val orderInfo = RetailMapper().mapToOrderInfo(message)
        orderService.updateOrderInfo(orderInfo)
    }

    companion object{
        val LOGGER: Logger = LoggerFactory.getLogger(Consumer::class.java)
    }
}