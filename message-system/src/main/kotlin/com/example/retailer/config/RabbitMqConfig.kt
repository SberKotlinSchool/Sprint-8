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

@Configuration
@EnableRabbit
class RabbitMqConfig {

    @Value("\${rabbitmq.queue}")
    lateinit var queueName: String

    @Value("\${rabbitmq.exchange.name}")
    lateinit var rabbitExchangeName: String

    @Value("\${rabbitmq.consumer.routing.key}")
    lateinit var consumerRoutingKey: String

    @Bean
    fun jsonMessageConverter(): MessageConverter = Jackson2JsonMessageConverter()

    @Bean
    fun exchange(): TopicExchange = TopicExchange(rabbitExchangeName)

    @Bean
    fun queue(): Queue = Queue(queueName)

    @Bean
    fun binding(): Binding = BindingBuilder.bind(queue())
        .to(exchange())
        .with("$consumerRoutingKey.#")

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = jsonMessageConverter()
        rabbitTemplate.setExchange(rabbitExchangeName)
        return rabbitTemplate
    }
}