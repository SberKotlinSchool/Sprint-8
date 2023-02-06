package com.example.retailer

import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration


@Configuration
@ComponentScan("com.example.retailer")
class AppConfig {

    @Bean
    fun hello(): Queue {
        return Queue("myQueue")
    }

    @Bean
    fun topic(): TopicExchange {
        return TopicExchange("distributor_exchange")
    }

    @Bean
    fun binding(topic: TopicExchange, queue: Queue): Binding {
        return BindingBuilder.bind(queue)
            .to(topic)
            .with("retailer.idromanova.#")
    }

    @Bean
    fun jsonMessageConverter(): Jackson2JsonMessageConverter {
        return Jackson2JsonMessageConverter()
    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate? {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = jsonMessageConverter()
        rabbitTemplate.setExchange("distributor_exchange")
        return rabbitTemplate
    }
}