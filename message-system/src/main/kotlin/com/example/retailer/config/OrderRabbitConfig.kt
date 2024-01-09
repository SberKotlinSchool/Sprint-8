package com.example.retailer.config

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service

@Configuration
class OrderRabbitConfig(
    private val objectMapper: ObjectMapper
) {

    @Bean("orderConnectionFactory")
    fun orderConnectionFactory() = CachingConnectionFactory()

    @Bean("orderRabbitAdmin")
    fun orderRabbitAdmin(
        @Qualifier("orderConnectionFactory") connectionFactory: ConnectionFactory
    ) = RabbitAdmin(connectionFactory)

    @Bean("orderRabbitQueue")
    fun orderRabbitQueue(): Queue {
        return Queue("order.queue", false)
    }

    @Bean("orderRabbitTopic")
    fun orderRabbitTopic(): TopicExchange {
        return TopicExchange("distributor_exchange")
    }

    @Bean("orderRabbitBind")
    fun orderRabbitBind(): Binding {
        return BindingBuilder.bind(orderRabbitQueue())
            .to(orderRabbitTopic())
            .with("retailer.DmitriyD1985.#")
    }

    @Bean("orderMessageConverter")
    fun orderMessageConverter() =
        Jackson2JsonMessageConverter(objectMapper)
}