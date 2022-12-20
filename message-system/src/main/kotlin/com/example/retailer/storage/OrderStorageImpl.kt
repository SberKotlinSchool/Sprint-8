package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import com.example.retailer.repository.OrderInfoRepository
import com.example.retailer.repository.OrderRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class OrderStorageImpl : OrderStorage {


    @Autowired
    private lateinit var orderRepository: OrderRepository

    @Autowired
    private lateinit var orderInfoRepository: OrderInfoRepository

    @Transactional
    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val order = orderRepository.save(draftOrder)
        val info = orderInfoRepository.save(OrderInfo(order.id!!, OrderStatus.SENT, "1234"))
        return PlaceOrderData(order, info)
    }

    override fun updateOrder(order: OrderInfo): Boolean {
        if (!orderInfoRepository.existsById(order.orderId)) {
            return false
        }
        orderInfoRepository.save(order)
        return true
    }

    override fun getOrderInfo(id: String): OrderInfo? = orderInfoRepository.findByIdOrNull(id)


}