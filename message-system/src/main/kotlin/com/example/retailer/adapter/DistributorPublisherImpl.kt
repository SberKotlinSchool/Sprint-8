package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class DistributorPublisherImpl(val rabbitTemplate: RabbitTemplate) : DistributorPublisher {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @Value("\${distributor.routing.key}")
    private val routingKey: String = ""

    @Value("\${exchange.name}")
    private val exchangeName: String = ""

    @Value("\${header.exchange.name}")
    private val exchangeHeaderName: String = "Notify-Exchange"

    @Value("\${header.routing.key.name}")
    private val routingKeyHeaderName: String = "Notify-RoutingKey"

    @Value("\${header.routing.key.value}")
    private val routingKeyHeaderValue: String = ""

    override fun placeOrder(order: Order): Boolean =
        runCatching {
            rabbitTemplate.convertAndSend(exchangeName, routingKey.format(order.id!!), order) {
                it.messageProperties.headers.putAll(
                    mapOf(
                        Pair(exchangeHeaderName, exchangeName),
                        Pair(routingKeyHeaderName, routingKeyHeaderValue)
                    )
                )
                it
            }
        }.onSuccess { logger.info("Send message $order") }
            .onFailure { ex -> logger.error(ex.localizedMessage) }
            .isSuccess
}