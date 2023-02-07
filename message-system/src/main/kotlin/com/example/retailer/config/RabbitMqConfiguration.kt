package com.example.retailer.config

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
open class RabbitMqConfiguration {

    @Value("\${queue.name}")
    private lateinit var queueName: String
    @Value("\${exchange.name}")
    private lateinit var exchangeName: String
    @Value("\${retailer.routing.key}")
    private lateinit var retailerRoutingKey: String

    companion object {
        const val NOTIFY_EXCHANGE = "Notify-Exchange"
        const val NOTIFY_ROUTING_KEY = "Notify-RoutingKey"
    }

    @Bean
    fun queue() = Queue(queueName)

    @Bean
    fun topicExchange() = TopicExchange(exchangeName)

    @Bean
    fun binding(): Binding = BindingBuilder
            .bind(queue())
            .to(topicExchange())
            .with(retailerRoutingKey)

    @Bean
    fun jsonMessageConverter() = Jackson2JsonMessageConverter()

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = jsonMessageConverter()
        rabbitTemplate.setExchange(exchangeName)
        return rabbitTemplate
    }
}