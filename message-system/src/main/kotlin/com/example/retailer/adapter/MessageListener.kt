package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo

interface MessageListener {
    fun readResponse(message: OrderInfo)
}