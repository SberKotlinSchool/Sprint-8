package com.example.retailer.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMqConfiguration {

    @Value("\${messages.exchange.name}")
    private lateinit var exchangeName: String

    @Value("\${messages.exchange.notify-routingkey}")
    private lateinit var retailerRoutingKey: String


    @Bean
    fun queue() = Queue("DistributorRetailQueue")

    @Bean
    fun topicExchange() = TopicExchange(exchangeName)

    @Bean
    fun binding(): Binding = BindingBuilder
            .bind(queue())
            .to(topicExchange())
            .with("$retailerRoutingKey.#")

    @Bean
    fun jsonMessageConverter(): Jackson2JsonMessageConverter {
        val objectMapper = ObjectMapper()
                .registerModule(KotlinModule())
        return Jackson2JsonMessageConverter(objectMapper)
    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = jsonMessageConverter()
        rabbitTemplate.setExchange(exchangeName)
        return rabbitTemplate
    }
}