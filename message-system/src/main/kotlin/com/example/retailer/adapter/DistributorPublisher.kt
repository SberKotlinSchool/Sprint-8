package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import com.example.retailer.mapper.RetailMapper
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
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
    fun placeOrder(order: Order) : Boolean

}

@Service
class DistributorPublisherImpl(
    @Autowired
    val template: RabbitTemplate
) : DistributorPublisher {

    override fun placeOrder(order: Order): Boolean {
        val massage = RetailMapper().mapToJsonString(order)
        template.convertAndSend("distributor_exchange", "distributor.placeOrder.aimshenik.${order.id}", massage, headers())
        return true
    }

    private fun headers() = { msg : Message  ->
        msg.messageProperties.headers["Notify-Exchange"] = "distributor_exchange"
        msg.messageProperties.headers["Notify-RoutingKey"] = "retail.aimshenik"
        msg
    }
}