package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import com.example.retailer.repository.OrderInfoRepository
import com.example.retailer.repository.OrderRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class OrderStorageImpl(
    private val orderRepository: OrderRepository,
    private val orderInfoRepository: OrderInfoRepository
) : OrderStorage {

    private val logger = LoggerFactory.getLogger(OrderStorageImpl::class.java)

    @Transactional
    override fun createOrder(draftOrder: Order): PlaceOrderData {
        logger.info("Order id = {} created.", draftOrder.id)
        val order = orderRepository.save(draftOrder)
        val info = orderInfoRepository.save(OrderInfo(draftOrder.id!!, OrderStatus.SENT, ""))
        return PlaceOrderData(order, info)
    }

    override fun updateOrder(order: OrderInfo): Boolean {
        logger.info("Search order id = {} for update", order.orderId)

        if (!orderInfoRepository.existsById(order.orderId)) {
            logger.info("Order not found!")
            return false
        } else {
            logger.info("Update the found order")
            orderInfoRepository.save(order)
            return true
        }
    }

    override fun getOrderInfo(id: String): OrderInfo? {
        logger.info("Search for order id = {}", id)
        return orderInfoRepository.findByIdOrNull(id)
    }

}

