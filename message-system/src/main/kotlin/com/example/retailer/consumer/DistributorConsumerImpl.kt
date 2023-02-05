package com.example.retailer.consumer

import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.storage.OrderStorage
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DistributorConsumerImpl(@Autowired private val orderStorage: OrderStorage,
                              @Autowired private val objectMapper: ObjectMapper) : DistributorConsumer {


    @RabbitListener(queues = ["\${rabbitmq.retailer.queue.name}"])
    override fun updateStatus(message: String) : Boolean {
        return try {
            val orderInfo = objectMapper.readValue(message, OrderInfo::class.java)
            orderStorage.updateOrder(orderInfo)
            true
        } catch (ex: Exception) {
            false
        }
    }

}