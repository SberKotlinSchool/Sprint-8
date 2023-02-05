package com.example.retailer.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration


@Configuration
@ComponentScan("com.example.retailer")
class RabbitMQConfig {
    @Value("\${rabbitmq.retailer.queue.name}")
    private val queue: String? = null

    @Value("\${rabbitmq.exchange.name}")
    private val exchange: String? = null

    @Value("\${rabbitmq.retailer.routing.key}")
    private val routingKey: String? = null

    // spring bean for rabbitmq queue
    @Bean
    fun queue(): Queue? {
        return Queue(queue)
    }

    // spring bean for rabbitmq exchange
    @Bean
    fun exchange(): TopicExchange? {
        return TopicExchange(exchange)
    }

    // binding between queue and exchange using routing key
    @Bean
    fun binding(): Binding? {
        return BindingBuilder
            .bind(queue())
            .to(exchange())
            .with("$routingKey.#")
    }
}