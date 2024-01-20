package com.example.retailer.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableRabbit
class RabbitMqConfiguration(
    @Value("\${rabbitmq.queue}") private val queueName: String,
    @Value("\${rabbitmq.exchange.name}") private val exchangeName: String,
    @Value("\${consumer.routing.prefix}") private val consumerRoutingPrefix: String
) {
    @Bean
    fun jsonMessageConverter(): Jackson2JsonMessageConverter {
        val objectMapper = ObjectMapper()
            .registerModule(KotlinModule())
        return Jackson2JsonMessageConverter(objectMapper)
    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val template = RabbitTemplate(connectionFactory)
        template.messageConverter = jsonMessageConverter()
        return template
    }

    @Bean
    fun exchange(): TopicExchange = TopicExchange(exchangeName)

    @Bean
    fun queue(): Queue = Queue(queueName)

    @Bean
    fun binding(): Binding = BindingBuilder.bind(queue())
        .to(exchange())
        .with("$consumerRoutingPrefix.#")
}