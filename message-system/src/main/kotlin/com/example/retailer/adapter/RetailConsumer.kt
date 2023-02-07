package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo

interface RetailConsumer {
    fun updateOrderStatus(orderInfo: OrderInfo)
}