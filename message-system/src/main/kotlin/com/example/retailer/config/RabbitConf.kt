package com.example.retailer.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.context.annotation.Configuration
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange
import org.springframework.context.annotation.Bean;

@Configuration
class RabbitConf {

    @Bean
    fun storeQueue(): Queue? {
        return Queue("store")
    }

    @Bean
    fun exchange(): TopicExchange {
        return TopicExchange("distributor_exchange")
    }

    @Bean
    fun bindingRetailer(exchange: TopicExchange, storeQueue: Queue) : Binding {
        return BindingBuilder
            .bind(storeQueue)
            .to(exchange)
            .with("retailer.Guzal1808.#")
    }
}