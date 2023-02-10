package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
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
class DistributorPublisherImpl @Autowired constructor(
    private val template: RabbitTemplate,
    @Value("\${rabbitmq.routingKey}") private val routingKey: String,
    @Value("\${rabbitmq.notifyRoutingKey}") private val notifyRoutingKey: String
) : DistributorPublisher {

    companion object {
        const val NOTIFY_EXCHANGE_HEADER = "Notify-Exchange"
        const val NOTIFY_ROUTING_KEY_HEADER = "Notify-RoutingKey"
    }

    override fun placeOrder(order: Order): Boolean =
        order.id?.let {
            template.convertAndSend(routingKey + ".${order.id}", order) {
                it.messageProperties.headers[NOTIFY_EXCHANGE_HEADER] = template.exchange
                it.messageProperties.headers[NOTIFY_ROUTING_KEY_HEADER] = notifyRoutingKey
                it
            }
            true
        } ?: false
}