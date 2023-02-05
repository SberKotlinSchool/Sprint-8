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
        val savedOrder  = orderRepository.save(draftOrder);
        val orderInfo =  OrderInfo(savedOrder.id!!, OrderStatus.CREATED, "")
        val savedInfo = orderInfoRepository.save(orderInfo)
        return PlaceOrderData(savedOrder, savedInfo!!);
    }

    override fun updateOrder(order: OrderInfo): Boolean {
        try{
            orderInfoRepository.save(order);
            return true;
        } catch (e: Exception)
        {
            return false;
        }
    }

    override fun getOrderInfo(id: String): OrderInfo? {
       return orderInfoRepository.findByIdOrNull(id);
    }
}