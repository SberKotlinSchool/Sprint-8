package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DistributorPublisherImpl : DistributorPublisher {

    @Autowired
    private lateinit var topic: TopicExchange

    @Autowired
    private lateinit var rabbitTmp: RabbitTemplate

    val mapper: ObjectMapper = ObjectMapper();

    override fun placeOrder(order: Order): Boolean {
        return try {
            val msg = mapper.writeValueAsString(order);
            rabbitTmp.convertAndSend(
                topic.name,
                "distributor.placeOrder.Guzal1808.${order.id}",
                msg
            ) { message ->
                message.messageProperties.headers["Notify-Exchange"] = "distributor_exchange"
                message.messageProperties.headers["Notify-RoutingKey"] = "retailer.Guzal1808"
                message
            }
            true;
        } catch (e: Exception) {
            println("Ошибка отправки заказа")
            false;
        }
    }
}