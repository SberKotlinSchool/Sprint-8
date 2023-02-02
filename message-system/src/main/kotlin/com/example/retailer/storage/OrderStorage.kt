package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import com.example.retailer.repository.OrderInfoRepository
import com.example.retailer.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional


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
    fun updateOrder(order_info: OrderInfo) : Boolean

    /**
     * Получение информации о заявке по id или null если не найдено
     */
    fun getOrderInfo(id: String) : OrderInfo?

}

@Service
class OrderStorageImpl(
    @Autowired val orderInfoRepository: OrderInfoRepository,
    @Autowired val orderRepository: OrderRepository
) : OrderStorage {

    @Transactional
    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val order = orderRepository.save(draftOrder)
        val orderInfo = OrderInfo(order.id!!, OrderStatus.SENT, "")
        orderInfoRepository.save(orderInfo)
        return PlaceOrderData(order, orderInfo)
    }

    override fun updateOrder(order_info: OrderInfo): Boolean {
        val orderInfo = orderInfoRepository.findById(order_info.orderId)
        return if (orderInfo.isPresent) {
            orderInfoRepository.save(order_info)
            true
        } else false
    }

    override fun getOrderInfo(id: String): OrderInfo? {
        return orderInfoRepository.findById(id).orElse(null)
    }

}