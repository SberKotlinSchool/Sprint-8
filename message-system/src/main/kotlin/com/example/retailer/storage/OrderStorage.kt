package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import com.example.retailer.repository.OrderInfoRepository
import com.example.retailer.repository.OrderRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

private const val DEFAULT_SIGNATURE = ""

/**
 * Интерфейс для организации хранилища заявок
 */
interface OrderStorage {

    /**
     * Первичное сохранение заявки в БД
     * Нужно вернуть объект с заполненным id
     */
    fun createOrder(draftOrder: Order) : PlaceOrderData

    /**
     * Обновление заявки
     */
    fun updateOrder(order: OrderInfo) : Boolean

    /**
     * Получение информации о заявке по id или null если не найдено
     */
    fun getOrderInfo(id: String) : OrderInfo?

}

@Component
class OrderStorageImpl(
    private val orderRepository: OrderRepository,
    private val orderInfoRepository: OrderInfoRepository
): OrderStorage {

    @Transactional
    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val savedOrder = orderRepository.save(draftOrder)
        val savedInfo = orderInfoRepository.save(
            OrderInfo(requireNotNull(savedOrder.id), OrderStatus.SENT, DEFAULT_SIGNATURE)
        )

        return PlaceOrderData(savedOrder, savedInfo)
    }

    @Transactional
    override fun updateOrder(order: OrderInfo): Boolean =
        runCatching {
            orderInfoRepository.save(order)
            true
        }.getOrDefault(false)

    override fun getOrderInfo(id: String): OrderInfo? =
        orderInfoRepository.findById(id).orElse(null)
}
