package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import com.example.retailer.repository.OrderInfoRepository
import com.example.retailer.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class OrderStorageImpl : OrderStorage {
    @Autowired
    lateinit var orderInfoRepository: OrderInfoRepository
    @Autowired
    lateinit var orderRepository: OrderRepository
    private var count = 2
    @Transactional
    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val order = orderRepository.save(draftOrder)
        val orderInfo = OrderInfo(order.id!!, OrderStatus.SENT, "")
        orderInfoRepository.save(orderInfo)
        return PlaceOrderData(order, orderInfo)
    }

    override fun updateOrder(order: OrderInfo): Boolean {
        val orderInfo = orderInfoRepository.findById(order.orderId)
        require(orderInfo.isPresent){
            order.status = OrderStatus.ERROR
            return false
        }
        if (count%2 == 0){
            order.status = OrderStatus.CREATED
            orderInfoRepository.save(order)
        } else{
            order.status = OrderStatus.DELIVERED
            orderInfoRepository.save(order)
        }
        count++
        return true
    }

    override fun getOrderInfo(id: String): OrderInfo? {
        return orderInfoRepository.findById(id).orElse(null)
    }

}