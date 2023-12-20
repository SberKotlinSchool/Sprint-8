package com.example.retailer.configurations

import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Config {
    @Bean
    fun topic() = TopicExchange("distributor_exchange")

    @Bean
    fun queue() = Queue("retailer.queue")

    @Bean
    fun binding(topic: TopicExchange, queue: Queue) =
        BindingBuilder
            .bind(queue)
            .to(topic)
            .with("retailer.ShedowSith.#")
}