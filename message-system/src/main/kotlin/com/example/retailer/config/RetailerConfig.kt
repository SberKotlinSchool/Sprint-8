package com.example.retailer.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class RetailerConfig {
    @Value("\${retailer.queue}")
    lateinit var queueName: String

    @Value("\${retailer.exchange}")
    lateinit var topicExchangeName: String

    @Value("\${retailer.github_user_name}")
    lateinit var githubUserName: String

    @Value("\${retailer.output_routing_key}")
    lateinit var outputRoutingKey: String

    @Value("\${retailer.input_routing_key}")
    lateinit var inputRoutingKey: String
}