package com.example.retailer.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.*
import org.springframework.amqp.core.BindingBuilder.bind
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("com.example.retailer")
class RabbitConfiguration {

    @Bean
    fun connectionFactory(): ConnectionFactory {
        return CachingConnectionFactory("localhost", 5673)
    }

    @Bean
    fun amqpAdmin(): AmqpAdmin {
        return RabbitAdmin(connectionFactory())
    }

    @Bean
    fun rabbitTemplate(): RabbitTemplate {
        return RabbitTemplate(connectionFactory())
    }

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
        "retail.aimshenik",
        null
    )

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(RabbitConfiguration::class.java)
    }
}