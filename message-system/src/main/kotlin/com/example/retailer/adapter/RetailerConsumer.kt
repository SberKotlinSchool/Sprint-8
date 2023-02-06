package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo

interface RetailerConsumer {
    fun receiveNotify(order: OrderInfo)
}
