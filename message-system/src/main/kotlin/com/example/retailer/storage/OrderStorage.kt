package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component


/**
 * Интерфейс для организации хранилища заявок
 */
interface OrderStorage {

    /**
     * Первичное сохранение заявки в БД
     * Нужно вернуть объект с заполненным id
     */
    fun createOrder(draftOrder: Order): PlaceOrderData

    /**
     * Обновление заявки
     */
    fun updateOrder(order: OrderInfo): Boolean

    /**
     * Получение информации о заявке по id или null если не найдено
     */
    fun getOrderInfo(id: String): OrderInfo?

}

@Component
class OrderStorageImpl(
    @Autowired val itemRepository: ItemRepository,
    @Autowired val orderRepository: OrderRepository,
    @Autowired val orderInfoRepository: OrderInfoRepository

) : OrderStorage {
    override fun createOrder(draftOrder: Order): PlaceOrderData {
        draftOrder.items.forEach { itemRepository.save(it) }
        val order = orderRepository.save(draftOrder)
        var orderInfo = OrderInfo(order.id!!, OrderStatus.SENT, "new order")
        orderInfo = orderInfoRepository.save(orderInfo)
        return PlaceOrderData(order, orderInfo)
    }

    override fun updateOrder(order: OrderInfo): Boolean {
        val fetched = orderInfoRepository.findById(order.orderId)
        return if (fetched.isPresent) {
            orderInfoRepository.save(order)
            true
        } else {
            false
        }
    }

    override fun getOrderInfo(id: String): OrderInfo? = orderInfoRepository.findByIdOrNull(id)

}
