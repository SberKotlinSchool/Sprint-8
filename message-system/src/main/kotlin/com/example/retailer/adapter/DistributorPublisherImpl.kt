package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import org.springframework.stereotype.Component

@Component
class DistributorPublisherImpl : DistributorPublisher {
    override fun placeOrder(order: Order): Boolean {
        return true
    }
}