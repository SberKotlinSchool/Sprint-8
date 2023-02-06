package com.example.retailer.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@EnableRabbit
@Configuration
class RabbitMQConfig {
    @Value("\${rabbitmq.queue}")
    private val queueName: String? = null

    @Value("\${rabbitmq.exchange}")
    private val exchange: String? = null

    @Value("\${rabbitmq.routingkey}")
    private val routingkey: String? = null

    @Value("\${rabbitmq.notifyRoutingKey}")
    private val notifyRoutingKey: String? = null

    @Bean
    fun queue(): Queue {
        return Queue(queueName, false)
    }

    @Bean
    fun exchange(): TopicExchange {
        return TopicExchange(exchange)
    }

    @Bean
    fun binding(queue: Queue, exchange: TopicExchange): Binding {
        return BindingBuilder.bind(queue).to(exchange).with(notifyRoutingKey + ".#")
    }

    @Bean
    fun jsonMessageConverter(): MessageConverter {
        val objectMapper = ObjectMapper()
        return Jackson2JsonMessageConverter(objectMapper)
    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = jsonMessageConverter()
        rabbitTemplate.setExchange(exchange)
        return rabbitTemplate
    }

    fun getRoutingkey() = routingkey

    fun getNotifyRoutingKey() = notifyRoutingKey
}