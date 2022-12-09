package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

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
    fun placeOrder(order: Order): Boolean

}

@Service
class DistributorPublisherImpl(
    @Autowired private val rabbitTemplate: RabbitTemplate,
    @Value("\${spring.rabbitmq.routingKey}") private val routingKey: String,
    @Value("\${spring.rabbitmq.notifyRoutingKey}") private val notifyRoutingKey: String
) : DistributorPublisher {


    override fun placeOrder(order: Order): Boolean {
        return if (order.id != null) {
            rabbitTemplate.convertAndSend(routingKey + ".${order.id}", order) {
                it.messageProperties.headers["Notify-Exchange"] = rabbitTemplate.exchange
                it.messageProperties.headers["Notify-RoutingKey"] = notifyRoutingKey
                it
            }
            true
        } else {
            false
        }
    }
}