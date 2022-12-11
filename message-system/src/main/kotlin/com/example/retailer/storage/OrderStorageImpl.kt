package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.mapper.OrderInfoMapper
import com.example.retailer.mapper.OrderMapper
import com.example.retailer.model.DataOrder
import com.example.retailer.model.DataOrderInfo
import com.example.retailer.repository.DataItemRepository
import com.example.retailer.repository.DataOrderInfoRepository
import com.example.retailer.repository.DataOrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderStorageImpl : OrderStorage {

    @Autowired
    private lateinit var orderMapper: OrderMapper

    @Autowired
    private lateinit var orderInfoMapper: OrderInfoMapper

    @Autowired
    private lateinit var dataItemRepository: DataItemRepository

    @Autowired
    private lateinit var dataOrderRepository: DataOrderRepository

    @Autowired
    private lateinit var dataOrderInfoRepository: DataOrderInfoRepository

    @Transactional
    override fun createOrder(draftOrder: Order): PlaceOrderData {

        val dataOrder = orderMapper.toEntity(draftOrder)

        dataItemRepository.saveAll(dataOrder.items)
        dataOrderRepository.save(dataOrder)

        val dataOrderInfo = DataOrderInfo(order = dataOrder, signature = getSignature(dataOrder))

        dataOrderInfoRepository.save(dataOrderInfo)

        val order = orderMapper.toDto(dataOrderInfo.order)
        val orderInfo = orderInfoMapper.toDto(dataOrderInfo)

        return PlaceOrderData(order, orderInfo)
    }

    private fun getSignature(dataOrder: DataOrder) = dataOrder.hashCode().toString()

    override fun updateOrder(orderInfo: OrderInfo): Boolean {


        val entity = dataOrderInfoRepository.getDataOrderInfoByOrderId(orderInfo.orderId.toLong())
        entity.status = orderInfo.status

        dataOrderInfoRepository.saveAndFlush(entity)

        return true
    }

    override fun getOrderInfo(id: String): OrderInfo? {
        val dataOrderInfo = dataOrderInfoRepository.getDataOrderInfoByOrderId(id.toLong())
        return orderInfoMapper.toDto(dataOrderInfo)
    }
}