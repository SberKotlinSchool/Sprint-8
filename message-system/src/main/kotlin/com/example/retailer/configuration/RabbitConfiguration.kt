package com.example.retailer.configuration


import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfiguration {
    @Bean
    fun topic() = TopicExchange("distributor_exchange")

    @Bean
    fun queue() = Queue("retailer.queue")

    @Bean
    fun binding(topic: TopicExchange, queue: Queue) =
        BindingBuilder
            .bind(queue)
            .to(topic)
            .with("retailer.dokl57.#")
}