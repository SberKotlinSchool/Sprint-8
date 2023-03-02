package com.example.retailer.config

import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Configuration {
    @Bean
    fun topicExchange()
            = TopicExchange("distributor_exchange", true, false)

    @Bean
    fun queue()
            = Queue("retailer", false, false, true)

    @Bean
    fun bindingRetailer(topicExchange: TopicExchange, queue: Queue
    ) = BindingBuilder
        .bind(queue)
        .to(topicExchange)
        .with("retailer.SEINBEVR.#")
}