package com.example.retailer.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("com.example.retailer")
class RabbitConfiguration {

    @Bean
    fun retailerQueue(): Queue {
        return Queue("retailer_queue")
    }

    @Bean
    fun topicExchange(): TopicExchange {
        return TopicExchange("distributor_exchange")
    }

    @Bean
    fun bindingRetailer(): Binding = Binding(
        "retailer_queue",
        Binding.DestinationType.QUEUE,
        "distributor_exchange",
        "retail.aimshenik.#",
        null
    )
}