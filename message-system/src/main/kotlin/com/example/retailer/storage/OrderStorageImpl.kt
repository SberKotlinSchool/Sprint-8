package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import com.example.retailer.repository.OrderInfoRepository
import com.example.retailer.repository.OrderRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class OrderStorageImpl(
    private val orderRepository: OrderRepository,
    private val orderInfoRepository: OrderInfoRepository
) : OrderStorage {

    companion object {
        private val logger = LoggerFactory.getLogger(OrderStorageImpl::class.java)
    }

    override fun createOrder(draftOrder: Order): PlaceOrderData {
        logger.info("Создан заказ id = {}", draftOrder.id)
        val order = orderRepository.save(draftOrder)
        val orderInfo = OrderInfo(draftOrder.id!!, OrderStatus.SENT, "")
        val info = orderInfoRepository.save(orderInfo)
        return PlaceOrderData(order, info)
    }

    override fun updateOrder(order: OrderInfo): Boolean {
        logger.info("Поиск заказа id = {}", order.orderId)
        val orderInfo = getOrderInfo(order.orderId)
        return if (orderInfo != null) {
            logger.info("Заказ найден - обновление")
            orderInfoRepository.save(order)
            true
        } else {
            logger.info("Заказ не найден")
            false
        }
    }

    override fun getOrderInfo(id: String): OrderInfo? {
        logger.info("Поиск заказа id = {}", id)
        return orderInfoRepository.findByIdOrNull(id)
    }
}