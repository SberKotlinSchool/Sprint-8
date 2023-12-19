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
class QueueConfig(
    @Value("\${rabbitmq.queue.name}")
    private val queueName: String,
    @Value("\${rabbitmq.exchange.name}")
    private val exchangeName: String,
    @Value("\${rabbitmq.consumer.routing.key}")
    private val consumerRoutingKey: String
) {
    @Bean
    fun queue(): Queue = Queue(queueName)

    @Bean
    fun exchange(): TopicExchange = TopicExchange(exchangeName)

    @Bean
    fun jsonMessageConverter(): MessageConverter = Jackson2JsonMessageConverter()

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = jsonMessageConverter()
        rabbitTemplate.setExchange(exchangeName)
        return rabbitTemplate
    }

    @Bean
    fun binding(): Binding = BindingBuilder.bind(Queue(queueName)).to(exchange()).with("$consumerRoutingKey.#")
}