package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import com.example.retailer.repository.OrderInfoRepository
import com.example.retailer.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.Random

@Service
class OrderStorageImpl @Autowired constructor(
    private val orderRepository: OrderRepository,
    private val orderInfoRepository: OrderInfoRepository
) : OrderStorage {
    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val savedOrder = orderRepository.save(draftOrder)
        val orderInfo =
            orderInfoRepository.save(
                OrderInfo(
                    savedOrder.id!!,
                    OrderStatus.SENT,
                    Random().nextInt(8).toString()
                )
            )

        return PlaceOrderData(savedOrder, orderInfo)
    }

    override fun updateOrder(orderinfo: OrderInfo): Boolean {
        if (orderInfoRepository.existsById(orderinfo.orderId)) {
            orderInfoRepository.save(orderinfo)

            return true
        }

            return false
    }

    override fun getOrderInfo(id: String): OrderInfo? {
        return orderInfoRepository.findByIdOrNull(id)
    }
}