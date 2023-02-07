package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import com.example.retailer.repository.OrderInfoRepository
import com.example.retailer.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class OrderStorageImpl: OrderStorage {

    @Autowired
    private lateinit var orderRepository: OrderRepository;

    @Autowired
    private lateinit var orderInfoRepository: OrderInfoRepository;

    override fun createOrder(draftOrder: Order): PlaceOrderData {
        orderRepository.save(draftOrder);
        val orderInfo =  OrderInfo(draftOrder.id!!, OrderStatus.SENT, "")
        orderInfoRepository.save(orderInfo)
        return PlaceOrderData(draftOrder, orderInfo);
    }

    override fun updateOrder(order: OrderInfo): Boolean {
        val orderInfo = getOrderInfo(order.orderId)
        return if (orderInfo != null) {
            orderInfoRepository.save(order)
            true
        } else {
            false
        }
    }

    override fun getOrderInfo(id: String): OrderInfo? {
       return orderInfoRepository.findByIdOrNull(id);
    }
}