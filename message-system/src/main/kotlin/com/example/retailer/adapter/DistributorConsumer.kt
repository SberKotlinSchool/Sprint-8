package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo

interface DistributorConsumer {
    fun updateOrderInfo(orderInfo: OrderInfo)
}