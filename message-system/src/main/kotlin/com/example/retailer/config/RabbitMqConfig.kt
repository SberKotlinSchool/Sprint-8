package com.example.retailer.config

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@EnableRabbit
class RabbitMqConfig(
    @Value("\${rabbitmq.exchange.name}")
    private val rabbitExchangeName: String,

    @Value("\${consumer.routing.prefix}")
    private val consumerRoutingPrefix: String
) {
    @Bean
    fun jsonMessageConverter(): MessageConverter = Jackson2JsonMessageConverter()

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val template = RabbitTemplate(connectionFactory)
        template.messageConverter = jsonMessageConverter()
        return template
    }

    @Bean
    fun exchange(): TopicExchange = TopicExchange(rabbitExchangeName)

    @Bean
    fun queue(): Queue = AnonymousQueue()


    @Bean
    fun binding(): Binding = BindingBuilder.bind(queue())
        .to(exchange())
        .with("$consumerRoutingPrefix.#")
}