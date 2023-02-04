package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

private const val ROUTING_KEY_VALUE = "distributor.placeOrder.alexalekhin"

private const val NOTIFY_EXCHANGE_KEY = "Notify-Exchange"

private const val NOTIFY_ROUTING_KEY = "Notify-RoutingKey"
private const val NOTIFY_ROUTING_KEY_VALUE = "retailer.alexalekhin"

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

@Component
class DistributorPublisherImpl(private val template: RabbitTemplate) : DistributorPublisher {

    override fun placeOrder(order: Order): Boolean =
        runCatching {
            val hasId = order.id != null

            if (hasId) {
                template.convertAndSend("$ROUTING_KEY_VALUE.${order.id}", order) { message ->
                    message.apply {
                        messageProperties.headers.run {
                            set(NOTIFY_EXCHANGE_KEY, template.exchange)
                            set(NOTIFY_ROUTING_KEY, NOTIFY_ROUTING_KEY_VALUE)
                        }
                    }
                }
            }

            hasId
        }.getOrDefault(false)
}
