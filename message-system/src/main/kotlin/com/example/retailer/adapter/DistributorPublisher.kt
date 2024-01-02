package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * Интерфейс для отправки заказа дистрибьютору
 */
interface DistributorPublisher {

    /**
     * Метод для отправки заказа
     * должен попасть в "distributor_exchange" с ключом маршрутизации "distributor.placeOrder.githubName.orderId"
     * Для получения уведомления, заполняем заголовки:
     * Notify-Exchange = distributor_exchange
     * Notify-RoutingKey = ваш ключ маршрутизации по шаблону "retail.#github_username#"
     *
     * После некоторого времени уведомления будут поступать в distributor_exchange с ключом retail.#github_username#.#orderId#
     */
    fun placeOrder(order: Order) : Boolean

}

@Component
class DistributorPublisherImpl(
    private val template: RabbitTemplate,
    private val exchange: TopicExchange,
    @Value("\${consumer.routing.prefix}")
    private val consumerRoutingKeyPrefix: String,
    @Value("\${publisher.routing.format}")
    private val routingKeyFormat: String
) : DistributorPublisher {

    override fun placeOrder(order: Order): Boolean {
        val routingKey = routingKeyFormat.format(order.id)
        try {
            template.convertAndSend(exchange.name, routingKey, order) {
                it.messageProperties.headers["Notify-Exchange"] = exchange.name
                it.messageProperties.headers["Notify-RoutingKey"] = consumerRoutingKeyPrefix
                it
            }
        } catch (e: Exception) {
            return false
        }
        return true
    }
}

interface RetailerConsumer {
    fun receiveNotify(order: OrderInfo)
}


@Component
class RetailerConsumerImpl(
    private val orderService: OrderService
) : RetailerConsumer {

    @RabbitListener(queues = ["#{queue.name}"])
    override fun receiveNotify(order: OrderInfo) {
        orderService.updateOrderInfo(order)
    }
}