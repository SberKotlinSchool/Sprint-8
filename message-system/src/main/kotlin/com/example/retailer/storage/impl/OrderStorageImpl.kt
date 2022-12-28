package com.example.retailer.storage.impl

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus.SENT
import com.example.retailer.storage.OrderInfoRepository
import com.example.retailer.storage.OrderRepository
import com.example.retailer.storage.OrderStorage
import com.example.retailer.storage.PlaceOrderData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository


/**
 * Интерфейс для организации хранилища заявок
 */
@Repository
class OrderStorageImpl: OrderStorage {

    @Autowired
    private lateinit var orderInfoRepository: OrderInfoRepository

    @Autowired
    private lateinit var orderRepository: OrderRepository

    /**
     * Первичное сохранение заявки в БД
     * Нужно вернуть объект с заполненным id
     */
    override fun createOrder(draftOrder: Order) : PlaceOrderData {
        val order = orderRepository.save(draftOrder)
        val info = orderInfoRepository.save(OrderInfo(order.id!!, SENT, ""))
        return PlaceOrderData(order, info)
    }

    /**
     * Обновление заявки
     */
    override fun updateOrder(order: OrderInfo) : Boolean {
        if (!orderInfoRepository.existsById(order.orderId)) {
            return false
        }
        orderInfoRepository.save(order)
        return true
    }

    /**
     * Получение информации о заявке по id или null если не найдено
     */
    override fun getOrderInfo(id: String) : OrderInfo? {
        return orderInfoRepository.findByIdOrNull(id)
    }

}