package com.example.retailer.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ApplicationConfig {
    @Bean
    fun exchange(): TopicExchange {
        return TopicExchange("distributor_exchange")
    }

    @Bean
    fun queue(): Queue {
        return Queue("consumer")
    }

    @Bean
    fun bindingRetailer(
        exchange: TopicExchange, queue: Queue
    ): Binding {
        return BindingBuilder.bind(queue).to(exchange).with("retailer.gigi0907.#")
    }
}