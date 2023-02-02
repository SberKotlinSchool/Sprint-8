package com.example.retailer

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

const val RETAILER_QUEUE_NAME = "retailer_queue"

private const val NOTIFY_EXCHANGE_NAME = "distributor_exchange"
private const val NOTIFY_ROUTING_TOPIC = "retailer.alexalekhin.#"

@Configuration
class RetailerConfiguration {

    @Bean
    fun retailerQueue(): Queue =
        Queue(RETAILER_QUEUE_NAME)

    @Bean
    fun topicExchange(): TopicExchange =
        TopicExchange(NOTIFY_EXCHANGE_NAME)

    @Bean
    fun binding(): Binding =
        BindingBuilder
            .bind(retailerQueue())
            .to(topicExchange())
            .with(NOTIFY_ROUTING_TOPIC)

    @Bean
    fun messageConverter(): MessageConverter =
        Jackson2JsonMessageConverter()

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate =
        RabbitTemplate(connectionFactory).apply {
            messageConverter = messageConverter()
            setExchange(NOTIFY_EXCHANGE_NAME)
        }
}
