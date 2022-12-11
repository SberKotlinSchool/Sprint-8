package com.example.retailer.adapter

import org.springframework.amqp.core.Message

interface RetailerConsumer {
    fun listen(msg: Message)
}