package com.example.retailer.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "rabbitmq")
@ConstructorBinding
class RabbitProperties(
    val exchange: String,
    val queue: String,
    val routingKey: String,
    val notifyRoutingKey: String
)