package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

/**
 * Jpa-репозиторий для взаимодействия с сущностью OrderInfo
 */
interface OrderInfoRepository: JpaRepository<OrderInfo, String>

/**
 * Jpa-репозиторий для взаимодействия с сущностью Order
 */
interface OrderRepository: JpaRepository<Order, String>

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
    fun updateOrder(orderInfo: OrderInfo) : Boolean

    /**
     * Получение информации о заявке по id или null если не найдено
     */
    fun getOrderInfo(id: String) : OrderInfo?

}

@Repository
class OrderStorageImpl @Autowired constructor (
    private val infoRepository: OrderInfoRepository,
    private val orderRepository: OrderRepository
): OrderStorage {

    @Transactional
    override fun createOrder(draftOrder: Order): PlaceOrderData =
        orderRepository.save(draftOrder)
            .let { order -> infoRepository.save(OrderInfo(order.id!!, OrderStatus.SENT, ""))
                .let { info -> PlaceOrderData(order, info) } }


    override fun updateOrder(orderInfo: OrderInfo): Boolean =
        infoRepository.findById(orderInfo.orderId)
            .map { infoRepository.save(orderInfo).let { true } }
            .orElse(false)


    override fun getOrderInfo(id: String): OrderInfo? = infoRepository.findById(id).orElse(null)

}