package com.example.retailer.adapter

interface DistributorConsumer {
    fun receive(message: String)
}