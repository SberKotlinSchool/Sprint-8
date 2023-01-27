package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo

interface Listener {
    fun readResponse(message: OrderInfo)
}