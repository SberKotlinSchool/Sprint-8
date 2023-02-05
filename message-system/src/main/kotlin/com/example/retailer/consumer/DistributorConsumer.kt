package com.example.retailer.consumer

import com.example.retailer.api.distributor.OrderInfo

interface DistributorConsumer {

    fun updateStatus(message: String) : Boolean
}