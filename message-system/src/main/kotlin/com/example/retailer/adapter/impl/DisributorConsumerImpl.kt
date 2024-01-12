package com.example.retailer.adapter.impl

import com.example.retailer.adapter.DistributorConsumer
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class DistributorConsumerImpl(
    private val orderService: OrderService
) : DistributorConsumer {

    private val objectMapper = ObjectMapper()

    @RabbitListener(queues = ["retailer_queue"])
    override fun receive(message: String) {
        runCatching {
            val orderInfo = deserializeOrderInfo(message)
            orderService.updateOrderInfo(orderInfo)
        }.onFailure {
            it.printStackTrace()
        }
    }

    private fun deserializeOrderInfo(message: String): OrderInfo =
        objectMapper.readValue(message, OrderInfo::class.java)
}
