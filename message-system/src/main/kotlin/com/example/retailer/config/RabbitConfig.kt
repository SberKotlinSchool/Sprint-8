package com.example.retailer.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
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
class RabbitConfig {
    @Value("\${spring.rabbitmq.queue}")
    private lateinit var queue: String

    @Value("\${spring.rabbitmq.exchange}")
    private lateinit var exchange: String

    @Value("\${spring.rabbitmq.notifyRoutingKey}")
    private lateinit var notifyRoutingKey: String

    @Bean
    fun queue(): Queue {
        return Queue(queue)
    }

    @Bean
    fun topicExchange(): TopicExchange? {
        return TopicExchange(exchange)
    }

    @Bean
    fun binding(): Binding {
        return BindingBuilder
            .bind(queue())
            .to(topicExchange())
            .with(notifyRoutingKey)
    }

    @Bean
    fun jsonMessageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate? {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = jsonMessageConverter()
        rabbitTemplate.setExchange(exchange)
        return rabbitTemplate
    }
}