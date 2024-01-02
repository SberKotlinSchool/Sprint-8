package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


interface InfoRepo : JpaRepository<OrderInfo, String>

interface OrderRepo : JpaRepository<Order, String>
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

@Repository
class OrderStorageImpl(
    private val orderRepo: OrderRepo,
    private val infoRepo: InfoRepo
) : OrderStorage {

    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val order = orderRepo.save(draftOrder)
        val info = infoRepo.saveAndFlush(OrderInfo(order.id!!, OrderStatus.SENT, ""))
        return PlaceOrderData(order, info)
    }

    override fun updateOrder(order: OrderInfo): Boolean {
        try {
            infoRepo.save(order)
        } catch (e: IllegalArgumentException) {
            return false
        }
        return true
    }

    override fun getOrderInfo(id: String): OrderInfo? {
        return infoRepo.findById(id).orElse(null)
    }
}