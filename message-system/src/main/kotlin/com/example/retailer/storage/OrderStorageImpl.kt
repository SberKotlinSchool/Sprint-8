package com.example.retailer.storage

import com.example.retailer.repository.OrderInfoRepository
import com.example.retailer.repository.OrderRepository
import org.springframework.stereotype.Service
import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus

@Service
class OrderStorageImpl(
    private val orderRepository: OrderRepository,
    private val orderInfoRepository: OrderInfoRepository
) : OrderStorage {
  /**
   * Первичное сохранение заявки в БД
   * Нужно вернуть объект с заполненным id
   */
  override fun createOrder(draftOrder: Order): PlaceOrderData {
    val order = orderRepository.save(draftOrder)
    val info = orderInfoRepository.save(OrderInfo(order.id!!.toString(), OrderStatus.SENT, ""))
    return PlaceOrderData(order, info)
  }

  override fun updateOrder(order: OrderInfo): Boolean {
    return if (orderInfoRepository.findById(order.orderId).isPresent) {
      orderInfoRepository.save(order)
      true
    } else false
  }

  override fun getOrders(): List<OrderInfo> {
    return orderInfoRepository.findAll()
  }

  override fun getOrderInfo(id: String): OrderInfo? {
    return orderInfoRepository.findById(id).orElse(null)
  }
}