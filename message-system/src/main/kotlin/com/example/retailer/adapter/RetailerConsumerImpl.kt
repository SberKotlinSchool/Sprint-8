package com.example.retailer.adapter

import com.example.retailer.config.RetailerConfig
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RetailerConsumerImpl : RetailerConsumer {
    @Autowired
    private lateinit var config: RetailerConfig

    @RabbitListener(queues = ["retailer_orders"])
    override fun listen(msg: String) {


        TODO("Not yet implemented")
    }
}