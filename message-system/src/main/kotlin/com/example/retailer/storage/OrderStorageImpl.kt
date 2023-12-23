package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import com.example.retailer.repository.OrderInfoRepository
import com.example.retailer.repository.OrderRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class OrderStorageImpl(
    private val orderRepository: OrderRepository,
    private val orderInfoRepository: OrderInfoRepository
) :
    OrderStorage {
    private val logger = LoggerFactory.getLogger(OrderStorageImpl::class.java)

    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val order = orderRepository.save(draftOrder)
        val draftOrderInfo = OrderInfo(order.id!!, OrderStatus.SENT, "")
        val orderInfo = orderInfoRepository.save(draftOrderInfo)
        return PlaceOrderData(order, orderInfo)
    }

    override fun updateOrder(order: OrderInfo): Boolean {
        if (!orderInfoRepository.existsById(order.orderId)) {
            logger.info("updateOrder(): UPDATING ORDER WITH ID ${order.orderId} IS NOT EXIST")
            return false
        }
        orderInfoRepository.save(order)
        return true
    }

    override fun getOrderInfo(id: String): OrderInfo? {
        return orderInfoRepository.findById(id).get()
    }
}