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
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableRabbit
@EnableConfigurationProperties(RabbitProperties::class)
class RabbitConfig(private val rabbitProperties: RabbitProperties) {

    @Bean
    fun queue(): Queue {
        return Queue(rabbitProperties.queue)
    }

    @Bean
    fun topicExchange() = TopicExchange(rabbitProperties.exchange)

    @Bean
    fun binding(): Binding = BindingBuilder
            .bind(queue())
            .to(topicExchange())
            .with(rabbitProperties.notifyRoutingKey)

    @Bean
    fun jsonMessageConverter() = Jackson2JsonMessageConverter()

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory) = RabbitTemplate(connectionFactory).apply {
        messageConverter = jsonMessageConverter()
        setExchange(rabbitProperties.exchange)
    }

}