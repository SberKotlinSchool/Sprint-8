package com.example.retailer

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration


@Configuration
@ComponentScan("com.example.retailer")
class AppConfiguration {

    @Bean
    fun hello(): Queue {
        return Queue("hello")
    }

    @Bean
    fun topic(): TopicExchange {
        return TopicExchange("distributor_exchange")
    }

    @Bean
    fun binding(topic: TopicExchange, queue: Queue): Binding {
        return BindingBuilder.bind(queue)
            .to(topic)
            .with("retailer.alexeyRozhok.#")
    }
}