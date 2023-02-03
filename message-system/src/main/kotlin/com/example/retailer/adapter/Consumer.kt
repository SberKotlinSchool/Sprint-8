package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo

interface Consumer {
    fun receiveUpdate(massage: OrderInfo)
}