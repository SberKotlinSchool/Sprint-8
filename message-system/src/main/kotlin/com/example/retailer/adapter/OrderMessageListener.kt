package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import mu.KLogging
import org.springframework.amqp.core.ExchangeTypes
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@EnableRabbit
@Component
class OrderMessageListener(
    private val orderService: OrderService,
    private val objectMapper: ObjectMapper
) {
    @RabbitListener(
        bindings = [
            QueueBinding(
                Queue(
                    "retailer_queue",
                    durable = "false"
                ),
                exchange = Exchange(
                    "distributor_exchange",
                    type = ExchangeTypes.TOPIC
                ),
                key = ["retail.AntonyKon.#"]
            )
        ],
        admin = "distributionRabbitAdmin"
    )
    fun onMessage(message: Message) {
        logger.info("Получено сообщение:\n$message")
        val orderInfo = objectMapper.readValue<OrderInfo>(message.body)
        orderService.updateOrderInfo(orderInfo)
    }

    companion object: KLogging()
}