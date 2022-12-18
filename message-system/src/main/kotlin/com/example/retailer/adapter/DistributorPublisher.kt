package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
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
class DistributorPublisherImpl : DistributorPublisher {
    @Autowired
    private lateinit var template: RabbitTemplate

    @Autowired
    private lateinit var topic: TopicExchange

    override fun placeOrder(order: Order): Boolean {
        if (order.id == null) {
            return false
        }
        val mapper = jacksonObjectMapper()
        val massage = mapper.writeValueAsString(order)

        template.convertAndSend(topic.name, "distributor.placeOrder.ok.${order.id}", massage) {
            it.messageProperties.headers["Notify-Exchange"] = "distributor_exchange"
            it.messageProperties.headers["Notify-RoutingKey"] = "retailer.ok"
            it
        }
        return true

    }
}
