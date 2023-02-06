package com.example.retailer.config

import org.springframework.amqp.core.AnonymousQueue
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig(
    @Value("\${rabbit.exchange.name}")
    private val exchangeName: String,

    @Value("\${consumer.routing.prefix}")
    private val consumerRoutingKeyPrefix: String
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
    fun exchange(): TopicExchange {
        return TopicExchange(exchangeName)
    }

    @Bean
    fun queue(): Queue {
        return AnonymousQueue()
    }

    @Bean
    fun binding(exchange: TopicExchange, queue: Queue): Binding {
        return BindingBuilder.bind(queue)
            .to(exchange)
            .with("$consumerRoutingKeyPrefix.#")
    }
}
